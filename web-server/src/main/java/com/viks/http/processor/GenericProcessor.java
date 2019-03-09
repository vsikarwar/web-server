package com.viks.http.processor;

import java.util.ArrayList;
import java.util.List;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public class GenericProcessor implements Processor{
	
	static final List<Processor> processors = new ArrayList<Processor>();
	
	static {
		processors.add(new RequestProcessor());
		processors.add(new KeepAliveProcessor());
		processors.add(new BodyProcessor());
	}
	

	public ProcessorType process(Request request, Response response) {
		for(Processor processor : processors) {
			processor.process(request, response);
		}
		return null;
	}

}
