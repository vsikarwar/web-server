package com.viks.http.processor;

import com.viks.http.request.Request;
import com.viks.http.response.Response;
import com.viks.http.status.HTTPStatus;
import com.viks.http.version.HttpVersion;
import com.viks.http.version.VersionHandler;

public class VersionProcessor implements Processor{

	public ProcessorType process(Request request, Response response) {
		System.out.println("Version Processor");
		String rawVersion = request.getVersion();
		VersionHandler vHandler = new VersionHandler();
		HttpVersion httpVersion = vHandler.parseVersionString(rawVersion);
		boolean isVersionValid = vHandler.isVersionValid(httpVersion);
		
		if(isVersionValid) {
			return ProcessorType.METHOD_PROCESSOR;
		}else {
			response.setStatus(HTTPStatus.HTTP_VERSION_NOT_SUPPORTED);
			request.setPath("errors/500.html");
			return ProcessorType.PATH_PROCESSOR;
		}
	}

}
