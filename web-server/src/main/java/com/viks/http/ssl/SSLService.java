package com.viks.http.ssl;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;
import com.viks.http.main.service.AbstractService;
import com.viks.http.socket.SocketServer;

public class SSLService extends AbstractService{
	
	private final ConfigService config = ConfigService.getInstance();
	private final SSLServer server;
	
	public SSLService() {
		super("SSL-Service");
		this.server = new SocketServer(conf.getInt(Configs.PORT.config()), 
				this.conf.getInt(Configs.DEFAULT_THREADS.config()), 
				this.conf.getInt(Configs.MAX_THREADS.config()), 
				this.conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(Configs.SERVER_NAME.config()));
	}

	@Override
	protected void startInnerService() {
		
	}

	@Override
	protected void stopInnerService() {
		
	}

}
