package com.vklp.http.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.vklp.http.session.ServerSession;

public class SocketServer extends AbstractSocketServer{
	
	public SocketServer(int port,
            int defaultThreads,
            int maxThreads,
            int socketBufferSize,
            String serverName) {
        super(port, defaultThreads, maxThreads, socketBufferSize, serverName);

		
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
	                        sessionId));
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
