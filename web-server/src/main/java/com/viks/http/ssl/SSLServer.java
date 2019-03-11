package com.viks.http.ssl;

import java.io.File;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class SSLServer extends Thread{

	public SSLServer() {
		
	}
	
	
	public void run() {
		final char[] password = "yourPassword".toCharArray();

		try {
			final KeyStore keyStore = KeyStore.getInstance(new File("yourKeystorePath.jks"), password);

			final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);

			final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("NewSunX509");
			keyManagerFactory.init(keyStore, password);

			final SSLContext context = SSLContext.getInstance("TLS");//"SSL" "TLS"
			context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

			final SSLServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket serverSocket = ((SSLServerSocket) factory.createServerSocket(443));
		    //logger.trace("Listening on port: " + LISTENING_PORT);

		    // ...
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
