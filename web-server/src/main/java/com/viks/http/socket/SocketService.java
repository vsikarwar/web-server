package com.viks.http.socket;

import com.viks.http.main.service.AbstractService;
import com.viks.http.server.ServerConfig;
import com.viks.http.server.ServerConfig.ServerConfigs;

public class SocketService extends AbstractService{
	
	private final SocketServer server;
	private final ServerConfig conf;
	
	public SocketService(ServerConfig conf) {
		this.conf = conf;
		this.server = new SocketServer(this.conf.getInt(ServerConfigs.PORT.config()), 
				this.conf.getInt(ServerConfigs.DEFAULT_THREADS.config()), 
				this.conf.getInt(ServerConfigs.MAX_THREADS.config()), 
				this.conf.getInt(ServerConfigs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(ServerConfigs.SERVER_NAME.config()));
	}

	@Override
	protected void startInnerService() {
		this.server.start();
	}

	@Override
	protected void stopInnerService() {
		System.out.println("Shutting down server socket");
		server.shutdown();
		
	}

}
