package com.viks.http.request;

import java.util.HashMap;
import java.util.Map;

public class UriHandler {
	
	
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
	
	
	public void handle(Request req, String pathStr) {
		HttpUri uri = req.getUri();
		
		String path = pathStr;
		Map<String, String> params = uri.getQueryParams();
		boolean hasQueryString = false;
		
		if(pathStr.contains("?")) {
			hasQueryString = true;
			String[] splitPathWithParams = pathStr.split("?");
			path = splitPathWithParams[0];
			parseParams(params, splitPathWithParams[1]);
		}
		
		uri.setPath(path);
		uri.setQueryParams(params);
		uri.setHasParams(hasQueryString);
		
	}

	private void parseParams(Map<String, String> params, String pathWithParams){
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
	
	

}
