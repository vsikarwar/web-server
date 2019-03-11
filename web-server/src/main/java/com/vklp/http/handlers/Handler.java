package com.vklp.http.handlers;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;

public interface Handler {

	boolean canHandler(HttpRequest req, HttpResponse res);
	
	void handle(HttpRequest req, HttpResponse res);
}
