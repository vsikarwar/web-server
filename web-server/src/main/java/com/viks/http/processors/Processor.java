package com.viks.http.processors;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public interface Processor {
	
	public void process(Request req, Response res);

}
