package com.viks.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.StringTokenizer;

import com.viks.http.header.HttpHeaders;
import com.viks.http.version.HttpVersion;
import com.viks.http.version.VersionHandler;

public class RequestReader {
	
	private InputStream inputStream;
	private RequestParser parser;
	
	public RequestReader(InputStream inputStream) {
		this.inputStream = inputStream;
		this.parser = new RequestParser();
	}
	
	public Request read() {
		Request req = new Request();
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
		
		try {
			String requestLine = inputReader.readLine();
			parseRequestLine(req, requestLine);
			parseHeaders(req, inputReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return req;
	}
	
	private void parseRequestLine(Request request, String input) {
		StringTokenizer st = new StringTokenizer(input);
		
		if(!st.hasMoreTokens()) {
			System.out.println("BAD request");
		}
		
		String methodStr = st.nextToken().toUpperCase();
		HttpMethod method = new HttpMethod(methodStr);
		request.setMethod(method);
		
		if(!st.hasMoreTokens()) {
			System.out.println("BAD request");
		}
		
		String path = st.nextToken();
		UriHandler uriHandler = new UriHandler();
		uriHandler.handle(request, path);
		
		if(!st.hasMoreTokens()) {
			System.out.println("No version");
		}
		
		String version = st.nextToken();
		VersionHandler versionHandler = new VersionHandler();
		HttpVersion httpVersion = versionHandler.handle(version);
		request.setVersion(httpVersion);
		
	}
	
	private void parseHeaders(Request request, BufferedReader in) {
		HttpHeaders headers = request.getHeaders();

		try {
			String line = in.readLine();
			
	        while (line != null && !line.trim().isEmpty()) {
	            int p = line.indexOf(':');
	            if (p >= 0) {
	            	String key = line.substring(0, p).trim();
	            	String[] value = line.substring(p+1).trim().split(",");
	                headers.add(key, value);
	            }
	            line = in.readLine();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
