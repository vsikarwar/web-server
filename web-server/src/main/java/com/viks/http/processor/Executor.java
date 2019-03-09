package com.viks.http.processor;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public class Executor {
	
	private Request request;
	private Response response;
	
	public Executor(Request request, Response response) {
		this.request = request;
		this.response = response;
	}
	
	public void execute(ProcessorType type) {
		
		while(type != ProcessorType.END_OF_CHAIN) {
			Processor processor = ProcessorFactory.getProcessor(type);
			type = processor.process(request, response);
		}
		
	}

}
