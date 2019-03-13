package com.vklp.http.services;

import org.apache.log4j.Logger;

public class JMXService extends AbstractService{
	
	private static Logger logger = Logger.getLogger(JMXService.class);

	@Override
	protected void startInnerService() {
		// TODO Auto-generated method stub
		logger.info("Starting JMX service");
		
	}

	@Override
	protected void stopInnerService() {
		// TODO Auto-generated method stub
		logger.info("Stopping JMX Service");
	}

}
