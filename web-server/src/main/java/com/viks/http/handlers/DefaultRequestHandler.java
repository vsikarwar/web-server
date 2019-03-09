package com.viks.http.handlers;

import com.viks.http.processor.ProcessorFactory;
import com.viks.http.processor.ProcessorType;
import com.viks.http.request.Request;
import com.viks.http.response.Response;

public class DefaultRequestHandler extends AbstractRequestHandler{

	@Override
	public void requestHandler(Request req, Response res) {
		ProcessorFactory.getProcessor(ProcessorType.FILE_PROCESSOR).process(req, res);
		
	}

}
