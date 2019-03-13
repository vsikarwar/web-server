package com.vklp.http.handlers.core;

import org.apache.log4j.Logger;

import com.vklp.http.handlers.AbstractHandler;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;

public class POSTHandler extends AbstractHandler{
	
	private static final Logger logger = Logger.getLogger(POSTHandler.class)

	public void doPost(HttpRequest req, HttpResponse res)  throws Exception{
		// TODO Auto-generated method stub
		logger.info("POST is not implemented");
		logger.debug(req.getContent().toString());
	}

	@Override
	public void doHead(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doGet(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

}
