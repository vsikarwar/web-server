package com.viks.http.processor;

import java.util.Date;

import com.viks.http.request.Request;
import com.viks.http.response.Response;
import com.viks.http.status.HTTPStatus;

public class RequestProcessor implements Processor{

	public ProcessorType process(Request request, Response response) {
		System.out.println("Inside request processor");
		
		if(request.getMethod().equals("GET")) {
			response.setVersion(request.getVersion());
			response.setStatus(HTTPStatus.OK);
			response.setServer("Java HTTP Server : 1.0");
			response.setDate(new Date());
		}
		
		return null;
	}

}
