package com.viks.http.socket;

import org.apache.log4j.Logger;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;
import com.viks.http.main.service.AbstractService;

public class SocketService extends AbstractService{
	
	private static final Logger logger = Logger.getLogger(SocketService.class);
	
	private final SocketServer server;
	private final ConfigService conf;
	
	public SocketService(ConfigService conf) {
		super("Socket-Service");
		this.conf = conf;
		this.server = new SocketServer(this.conf.getInt(Configs.PORT.config()), 
				this.conf.getInt(Configs.DEFAULT_THREADS.config()), 
				this.conf.getInt(Configs.MAX_THREADS.config()), 
				this.conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(Configs.SERVER_NAME.config()));
	}

	@Override
	protected void startInnerService() {
		logger.info("Starting Service .. " + this.getType());
		this.server.start();
	}

	@Override
	protected void stopInnerService() {
		logger.info("Shutting down .. " + this.getType());
		server.shutdown();
		
	}

}
