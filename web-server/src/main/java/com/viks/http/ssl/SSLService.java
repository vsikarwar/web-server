package com.viks.http.ssl;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;
import com.viks.http.main.service.AbstractService;

public class SSLService extends AbstractService{
	
	private final ConfigService conf = ConfigService.getInstance();
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
		this.server.start();
	}

	@Override
	protected void stopInnerService() {
		this.server.stop();
	}

}
