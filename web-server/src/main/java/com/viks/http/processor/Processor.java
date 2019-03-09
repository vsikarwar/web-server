package com.viks.http.processor;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public interface Processor {

	ProcessorType process(Request request, Response response);
	
}
