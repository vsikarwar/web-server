package com.viks.http.request;

import java.util.StringTokenizer;

import com.viks.http.version.HttpVersion;
import com.viks.http.version.VersionHandler;

public class RequestParser {

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
	
	

}
