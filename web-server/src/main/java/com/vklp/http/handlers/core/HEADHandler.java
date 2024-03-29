package com.vklp.http.handlers.core;

import com.vklp.http.handlers.AbstractHandler;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class HEADHandler extends AbstractHandler{

	public void doHead(HttpRequest req, HttpResponse res)  throws Exception{
		String path = req.getUri();
		res.setContentType(fileService.getContentType(path));
		res.setContentLength(fileService.getContentLength(path));
		res.setStatus(HttpStatus.OK);
	}

	@Override
	public void doGet(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPost(HttpRequest req, HttpResponse res) {
		// TODO Auto-generated method stub
		
	}

}
