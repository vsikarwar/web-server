package com.viks.http.processor;

import java.io.File;

import com.viks.http.request.Request;
import com.viks.http.response.Response;
import com.viks.http.status.HTTPStatus;

public class PathProcessor implements Processor{

	public ProcessorType process(Request request, Response response) {
		System.out.println("Path processor");
		String path = request.getPath();
		
		//if path is allowed and valid
		File file = new File("./content", path);
		if(file.isFile()) {
			int fileLength = (int)file.length();
			response.setContentLength(fileLength);
		}else {
			response.setStatus(HTTPStatus.NOT_FOUND);
			request.setPath("errors/400.html");
		}
		
		return ProcessorType.FILE_PROCESSOR;
			
	}

}
