package com.vklp.http.handlers.basic;

import com.vklp.http.handlers.core.GETHandler;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class ErrorHandler extends GETHandler{

	public void doGet(HttpRequest req, HttpResponse res) {
		handleError(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
}
