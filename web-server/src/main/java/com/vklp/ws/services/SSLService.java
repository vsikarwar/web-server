package com.vklp.ws.services;

import org.apache.log4j.Logger;

import com.vklp.http.config.ConfigService;
import com.vklp.http.config.ConfigService.Configs;
import com.vklp.http.server.SSLServer;

public class SSLService extends AbstractService{
	
	private static final Logger logger = Logger.getLogger(SSLService.class);
	
	private final SSLServer server;
	
	public SSLService() {
		super("SSL-Service");
		this.server = new SSLServer(this.conf.getInt(Configs.SSL_PORT.config()), 
				this.conf.getInt(Configs.DEFAULT_THREADS.config()), 
				this.conf.getInt(Configs.MAX_THREADS.config()), 
				this.conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(Configs.SERVER_NAME.config()));
	}

	@Override
	protected void startInnerService() {
		logger.info("Starting ssl service");
		this.server.start();
	}

	@Override
	protected void stopInnerService() {
		logger.info("stopping ssl service");
		this.server.stop();
	}

}
