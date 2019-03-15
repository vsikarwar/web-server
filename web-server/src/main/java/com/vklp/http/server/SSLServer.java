package com.vklp.http.server;

import java.io.FileOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.session.ServerSession;

public class SSLServer extends AbstractSocketServer{
	
	public static final Logger logger = Logger.getLogger(SSLServer.class);
	
	private final RequestHandler handler;

	public SSLServer(Config conf, RequestHandler handler) {
		super(conf.getInt(Configs.SSL_PORT.config()), 
				conf.getInt(Configs.DEFAULT_THREADS.config()), 
				conf.getInt(Configs.MAX_THREADS.config()), 
				conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				conf.getStr(Configs.SERVER_NAME.config()));
		
		this.handler = handler;
		
	}
	
	
	public void run() {
		//TODO: remove it to configuration and encrypt it
		final char[] password = "viks-ws".toCharArray();

		try {
			
			final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileOutputStream fos = new FileOutputStream("viks-ws-keystore");
			keyStore.store(fos , password);
			//final KeyStore keyStore = KeyStore.getInstance(new File("viks-ws-keystore"), password);

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
			
			while(!isInterrupted() && !serverSocket.isClosed()) {
				logger.debug("secure accepting connections : ");
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
