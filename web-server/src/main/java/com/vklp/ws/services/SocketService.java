package com.vklp.ws.services;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.server.SocketServer;

public class SocketService extends AbstractService{
	
	private static final Logger logger = Logger.getLogger(SocketService.class);
	
	private final SocketServer server;
	
	public SocketService(Config conf, RequestHandler handler) {
		super("Socket-Service");
		this.server = new SocketServer(conf, handler);
	}

	@Override
	protected void startInnerService() {
		logger.info("Starting socket service");
		this.server.start();
	}

	@Override
	protected void stopInnerService() {
		logger.info("Shutting down socket service");
		server.shutdown();
		
	}

}
