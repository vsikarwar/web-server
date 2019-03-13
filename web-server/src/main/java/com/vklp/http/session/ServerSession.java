package com.vklp.http.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.request.RequestReader;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.ResponseWriter;

public class ServerSession implements Runnable{
	
	private final static Logger logger = Logger.getLogger(ServerSession.class);
	
    private final Map<Long, ServerSession> activeSessions;
    private final long sessionId;
    private final Socket socket;
    private volatile boolean isClosed = false;
    private final RequestHandler handler;
    
    
    public ServerSession(Map<Long, ServerSession> activeSessions,
    							Socket socket,
                                long id,
                                RequestHandler handler) {
    	this.activeSessions = activeSessions;
        this.socket = socket;
        this.sessionId = id;
    	this.handler = handler;
    }
    
    public Socket getSocket() {
        return socket;
    }

    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }


	public void run() {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		
		try {
			activeSessions.put(sessionId, this);
			logger.debug("Active session : " + sessionId + "/" + activeSessions.size() + " connection from : " + socket.getRemoteSocketAddress());
			
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			while(!isInterrupted() && !socket.isClosed() && !isClosed) {
				HttpResponse response = new HttpResponse();
				
				RequestReader requestReader = new RequestReader(inputStream);
				ResponseWriter responseWriter = new ResponseWriter(outputStream);
				
				HttpRequest request = requestReader.readRequest();
				
				if(request != null) {
					handler.handle(request, response);
					
					responseWriter.write(response);
				}
				
				//If we are sending Connection header as keep-alive means we need to keep the socket open
				//if we send connection header as close means we can close the socket
				if(null != response.getHeader(Headers.CONNECTION) &&
						("keep-alive").equals(response.getHeader(Headers.CONNECTION)) && 
						response.containsHeader(Headers.KEEP_ALIVE)) {
					socket.setKeepAlive(true);
					socket.setSoTimeout(6000);
				}else {
					close();
				}
				
				outputStream.flush();
			}
			
		}catch(SocketTimeoutException ste) {
			logger.error("Socket timeout exception occurred for " + this.sessionId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if(!socket.isClosed()) {
                    socket.close();
                    logger.error("Closing connection !!! " + this.sessionId);
                }
            } catch(Exception e) {
            	logger.error("Error while closing the socket : " + e.getMessage());
            }
            // now remove ourselves from the set of active sessions
            this.activeSessions.remove(sessionId);
        }
	}
	
	public void close() throws IOException {
        this.isClosed = true;
    }

}
