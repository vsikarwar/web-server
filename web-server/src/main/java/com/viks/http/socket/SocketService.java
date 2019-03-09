package com.viks.http.socket;

import com.viks.http.main.service.AbstractService;
import com.viks.http.request.RequestHandler;

public class SocketService extends AbstractService{
	
	private final SocketServer server;
	//private final RequestHandler handlerFactory = new RequestHandler();
	
	public SocketService() {
		this.server = new SocketServer(8080, 4, 10, 1000, "VIKS-web-server");
	}

	@Override
	protected void startInnerService() {
		this.server.start();
	}

	@Override
	protected void stopInnerService() {
		//server.shutdown();
		
	}

}
