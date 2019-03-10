package com.viks.http.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import com.viks.http.header.Headers;
import com.viks.http.processors.RequestHandler;
import com.viks.http.request.Request;
import com.viks.http.request.RequestReader;
import com.viks.http.response.Response;
import com.viks.http.response.ResponseWriter;

public class SocketServerSession implements Runnable{
	
    private final Map<Long, SocketServerSession> activeSessions;
    private final long sessionId;
    private final Socket socket;
    private volatile boolean isClosed = false;
    
    
    public SocketServerSession(Map<Long, SocketServerSession> activeSessions,
    							Socket socket,
                                long id) {
    	this.activeSessions = activeSessions;
        this.socket = socket;
        this.sessionId = id;
    	
    }
    
    public Socket getSocket() {
        return socket;
    }

    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }


	public void run() {
		BufferedReader in = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		
		System.out.println("Inside socket server session");
		try {
			activeSessions.put(sessionId, this);
			System.out.println(activeSessions.keySet());
			
			System.out.println(socket.getRemoteSocketAddress());
			
			inputStream = socket.getInputStream();
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			outputStream = socket.getOutputStream();
			
			
			
			while(!isInterrupted() && !socket.isClosed() && !isClosed) {
				
				if(in.ready()) {
					Request request = new Request();
					Response response = new Response();
					
					RequestReader requestReader = new RequestReader(inputStream);
					ResponseWriter responseWriter = new ResponseWriter(outputStream);
					
					requestReader.read(request);
					
					RequestHandler handler = new RequestHandler();
					handler.handle(request, response);
					
					responseWriter.write(response);
	
					if(null != response.getHeaders().get(Headers.CONNECTION.getName()) &&
							("keep-alive").equals(response.getHeaders().get(Headers.CONNECTION.getName()))) {
						socket.setKeepAlive(true);
						socket.setSoTimeout(6000);
						System.out.println("******Setting timeout");
					}else {
						close();
					}
					
					outputStream.flush();
				}
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if(!socket.isClosed()) {
                    socket.close();
                    System.out.println("Closing connection !!! ");
                }
            } catch(Exception e) {
            	e.printStackTrace();
            }
            // now remove ourselves from the set of active sessions
            this.activeSessions.remove(sessionId);
        }
	}
	
	public void close() throws IOException {
        this.isClosed = true;
        //this.socket.close();
    }

}
