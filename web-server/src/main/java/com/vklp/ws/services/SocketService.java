package com.vklp.ws.services;

import org.apache.log4j.Logger;

import com.vklp.http.config.ConfigService;
import com.vklp.http.config.ConfigService.Configs;
import com.vklp.http.server.SocketServer;

public class SocketService extends AbstractService{
	
	private static final Logger logger = Logger.getLogger(SocketService.class);
	
	private final SocketServer server;
	
	public SocketService() {
		super("Socket-Service");
		this.server = new SocketServer(this.conf.getInt(Configs.PORT.config()), 
				this.conf.getInt(Configs.DEFAULT_THREADS.config()), 
				this.conf.getInt(Configs.MAX_THREADS.config()), 
				this.conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(Configs.SERVER_NAME.config()));
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
