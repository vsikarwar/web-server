package com.viks.http.ssl;

import java.io.File;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import com.viks.http.socket.AbstractSocketServer;
import com.viks.http.socket.SocketServerSession;

public class SSLServer extends AbstractSocketServer{

	public SSLServer(int port,
            int defaultThreads,
            int maxThreads,
            int socketBufferSize,
            String serverName) {
        super(port, defaultThreads, maxThreads, socketBufferSize, serverName);
		
	}
	
	
	public void run() {
		final char[] password = "viks-ws".toCharArray();

		try {
			
			//System.setProperty("javax.net.ssl.keyStore", "viks-ws-keystore");
		    //System.setProperty("javax.net.ssl.keyStorePassword", "viks-ws");
		    
		    
			final KeyStore keyStore = KeyStore.getInstance(new File("viks-ws-keystore"), password);

			final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);

			final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("NewSunX509");
			keyManagerFactory.init(keyStore, password);

			final SSLContext context = SSLContext.getInstance("TLS");//"SSL" "TLS"
			context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

			final SSLServerSocketFactory factory = context.getServerSocketFactory();
			serverSocket = ((SSLServerSocket) factory.createServerSocket(port));
			
			serverSocket.setReuseAddress(true);
			serverSocket.setReceiveBufferSize(this.socketBufferSize);
			
		    
		    /*SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		    serverSocket = ssf.createServerSocket(1234);*/
		    
			while(!isInterrupted() && !serverSocket.isClosed()) {
				System.out.println("secure accepting connections : ");
				try {
					final Socket socket = serverSocket.accept();
					System.out.println("Socket accepting connections : " + socket.getRemoteSocketAddress());
					//configure socket
					long sessionId = this.sessionIdSequence.getAndIncrement();
					this.threadPool.execute(new SocketServerSession(activeSessions,
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
