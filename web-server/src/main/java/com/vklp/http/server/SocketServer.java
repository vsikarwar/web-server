package com.vklp.http.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.session.ServerSession;

public class SocketServer extends AbstractSocketServer{
	
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
		System.out.println("Starting socket server on port " + port);
		
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(port));
			serverSocket.setReceiveBufferSize(this.socketBufferSize);
			
			while(!isInterrupted() && !serverSocket.isClosed()) {
				System.out.println("Socket accepting connections : ");
				try {
					final Socket socket = serverSocket.accept();
					System.out.println("Socket accepting connections : " + socket.getRemoteSocketAddress());
					//configure socket
					long sessionId = this.sessionIdSequence.getAndIncrement();
					this.threadPool.execute(new ServerSession(activeSessions,
	                        socket,
	                        sessionId,
	                        handler));
					System.out.println("POOL SIZE : " + threadPool.getPoolSize());
				}catch(SocketException se) {
					se.printStackTrace();
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
