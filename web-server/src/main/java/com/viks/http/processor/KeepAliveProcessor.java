package com.viks.http.processor;

import java.util.Map;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public class KeepAliveProcessor implements Processor{

	public ProcessorType process(Request request, Response response) {
		System.out.println("Keep Alive processor");
		
		Map<String, String> reqHeaders = request.getHeaders();
		
		if(reqHeaders.containsKey("connection")) {
			if(reqHeaders.get("connection").equals("keep-alive")) {
				request.setKeepAlive(true);
				response.setKeepAlive(true);
				Map<String, String> resHeaders = response.getHeaders();
				resHeaders.put("Connection", "keep-alive");
				resHeaders.put("Keep-Alive", "timeout=3000");
			}
		}
		return null;
	}

}
