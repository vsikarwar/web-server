package com.vklp.http.message.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpParams {
	
	private Map<String, String> params;
	
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
	
	public HttpParams() {
		params = new HashMap<String, String>();
	}
	
	public void clear() {
		params.clear();
	}
	
	public Iterator<String> getNames(){
		return params.keySet().iterator();
	}
	
	public String getValue(String name) {
		return params.get(name);
	}
	
	public void put(String name, String value) {
		params.put(name, value);
	}
	
	public void putParamLine(String params) {
		putPairs(params, "&");
	}
	
	public void putPairs(String line, String lineSplitter) {
		String[] params = line.split(lineSplitter);
		for(String param : params) {
			putPair(param);
		}
	}
	
	public void putPair(String param) {
		putPair(param, "=");
	}
	
	public void putPair(String param, String regex) {
		String[] items = param.split(regex);
		if(items.length == 2) {
			String key = items[0];
			String value = items[1];
			value = decodeURI(value);
			put(key, value);
		}
		
		if(items.length > 2) {
			String key = items[0];
			String value = "";
			for(int i = 1; i< items.length; i++) {
				value += items[i];
			}
			value = decodeURI(value);
			put(key, value);
		}
	}
	
	public String decodeURI(String value) {
		for(Map.Entry<String, String> entry : decoderPairs.entrySet()) {
			value = value.replaceAll(entry.getKey(), entry.getValue());
		}
		return value;
	}
	

}
