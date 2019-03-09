package com.viks.http.handlers;

import com.viks.http.processor.ProcessorFactory;
import com.viks.http.processor.ProcessorType;
import com.viks.http.request.Request;
import com.viks.http.response.Response;

public abstract class AbstractRequestHandler {
	
	private void preProcess(Request req, Response res) {
		//if version is supported or not
		ProcessorFactory.getProcessor(ProcessorType.VERSION_PROCESSOR).process(req, res);
		
		//if method is supported or not
		ProcessorFactory.getProcessor(ProcessorType.METHOD_PROCESSOR).process(req, res);
		
		//path processor
		ProcessorFactory.getProcessor(ProcessorType.PATH_PROCESSOR).process(req, res);
	}
	
	public abstract void requestHandler(Request req, Response res);
	
	public void handle(Request req, Response res) {
		preProcess(req, res);
		requestHandler(req, res);
	}

}
