package com.viks.http.processor;

import com.viks.http.request.Request;
import com.viks.http.response.Response;
import com.viks.http.status.HTTPStatus;

public class MethodProcessor implements Processor{

	public ProcessorType process(Request request, Response response) {
		System.out.println("Method processor");
		String method = request.getMethod();
		if(method.equals("GET")) {
			return ProcessorType.PATH_PROCESSOR;
		}else {
			response.setStatus(HTTPStatus.METHOD_NOT_ALLOWED);
			request.setPath("errors/400.html");
			return ProcessorType.PATH_PROCESSOR;
		}
	}

}
