package com.viks.http.processors;

import com.viks.http.message.request.HttpRequest;
import com.viks.http.message.response.HttpResponse;

public interface Processor {
	
	public void process(HttpRequest req, HttpResponse res);

}
