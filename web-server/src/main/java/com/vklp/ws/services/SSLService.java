package com.vklp.ws.services;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.server.SSLServer;

public class SSLService extends AbstractService{
	
	private static final Logger logger = Logger.getLogger(SSLService.class);
	
	private final SSLServer server;
	
	
	public SSLService(Config conf, RequestHandler handler) {
		super("SSL-Service");
		this.server = new SSLServer(conf, handler);
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
