package com.viks.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestHandler {
	
	private static final RequestHandler INSTANCE = new RequestHandler();
	
	private static Map<String, String> decoderPairs = new HashMap<String, String>();
    static {
        decoderPairs.put("%20", " ");
        decoderPairs.put("%3C", "<");
        decoderPairs.put("%2C", ",");
        decoderPairs.put("%3E", ">");
        decoderPairs.put("%3D", "=");
        decoderPairs.put("%3B", ";");
        decoderPairs.put("%2B", "+");
        decoderPairs.put("%26", "&");
        decoderPairs.put("%40", "@");
        decoderPairs.put("%23", "#");
        decoderPairs.put("%24", "\\$");
        decoderPairs.put("%5B", "[");
        decoderPairs.put("%5D", "]");
        decoderPairs.put("%3A", ":");
        decoderPairs.put("%22", "\"");
        decoderPairs.put("%3F", "\\?");
    }
	
	
	private RequestHandler() {}
	
	public static RequestHandler getInstance() {
		return INSTANCE;
	}
	
	private void parseRequestLine(Request request, String input) {
		StringTokenizer st = new StringTokenizer(input);
		
		if(!st.hasMoreTokens()) {
			System.out.println("BAD request");
		}
		
		String method = st.nextToken().toUpperCase();
		request.setMethod(method);
		
		if(!st.hasMoreTokens()) {
			System.out.println("BAD request");
		}
		
		String path = st.nextToken();
		if(path.contains("?")) {
			String[] splitPathWithParams = path.split("?");
			request.setPath(splitPathWithParams[0]);
			parseParams(request, splitPathWithParams[1]);
		}else {
			request.setPath(path);
		}
		
		if(!st.hasMoreTokens()) {
			System.out.println("No version");
		}
		
		String version = st.nextToken();
		request.setVersion(version);
		
	}
	
	private void parseParams(Request request, String pathWithParams){
		Map<String, String> params = request.getQueryParams();
		
		String[] allParams = pathWithParams.split("&");
		for(String singleParam : allParams) {
			String[] pair = singleParam.split("=");
			String key = pair[0];
			String value = pair[1];
			for(Map.Entry<String, String> entry : decoderPairs.entrySet()) {
				value = value.replaceAll(entry.getKey(), entry.getValue());
			}
			params.put(key, value);
		}
		
	}
	
	public boolean handle(Request request, BufferedReader reader) {
		boolean handled = false;
		try {
			//if(reader.ready()) {
				parseRequestLine(request, reader.readLine());
				parseHeaders(request, reader);
				//payload handler for post method
				handled = true;
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(request);
		
		return handled;
	}
	
	private void parseHeaders(Request request, BufferedReader in) {
		Map<String, String> headers = request.getHeaders();

		try {
			String line = in.readLine();
			
	        while (line != null && !line.trim().isEmpty()) {
	            int p = line.indexOf(':');
	            if (p >= 0) {
	                headers.put(line.substring(0, p).trim().toLowerCase(Locale.US), line.substring(p + 1).trim());
	            }
	            line = in.readLine();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
