package com.vklp.http.handlers;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class ErrorHandler extends AbstractHandler{

	public void handle(HttpRequest req, HttpResponse res) {
		handleError(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	void doHead(HttpRequest req, HttpResponse res) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	void doGet(HttpRequest req, HttpResponse res) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	void doPost(HttpRequest req, HttpResponse res) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
