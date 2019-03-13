package com.vklp.http.services;

import org.apache.log4j.Logger;

public class WebSocketService extends AbstractService{
	
	private static final Logger logger = Logger.getLogger(WebSocketService.class);

	@Override
	protected void startInnerService() {
		// TODO Auto-generated method stub
		logger.info("Starting WebSocketService");
		
	}

	@Override
	protected void stopInnerService() {
		// TODO Auto-generated method stub
		logger.info("Stopping websocket service");
	}

}
