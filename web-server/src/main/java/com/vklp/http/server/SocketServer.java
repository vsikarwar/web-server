package com.vklp.http.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.session.ServerSession;
import com.vklp.ws.services.SocketService;

public class SocketServer extends AbstractSocketServer{
	
	private static final Logger logger = Logger.getLogger(SocketService.class);
	
	private final RequestHandler handler;
	
	public SocketServer(Config conf, RequestHandler handler) {
        super(conf.getInt(Configs.PORT.config()), 
				conf.getInt(Configs.DEFAULT_THREADS.config()), 
				conf.getInt(Configs.MAX_THREADS.config()), 
				conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				conf.getStr(Configs.SERVER_NAME.config()));
        
        this.handler = handler;
		
	}
	
	public void run() {
		logger.debug("Starting socket server on port " + port);
		
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(port));
			serverSocket.setReceiveBufferSize(this.socketBufferSize);
			
			while(!isInterrupted() && !serverSocket.isClosed()) {
				logger.debug("Socket accepting connections : ");
				try {
					final Socket socket = serverSocket.accept();
					logger.debug("Socket accepting connections : " + socket.getRemoteSocketAddress());
					//configure socket
					long sessionId = this.sessionIdSequence.getAndIncrement();
					this.threadPool.execute(new ServerSession(activeSessions,
	                        socket,
	                        sessionId,
	                        handler));
					logger.debug("POOL SIZE : " + threadPool.getPoolSize());
				}catch(SocketException se) {
					se.printStackTrace();
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
