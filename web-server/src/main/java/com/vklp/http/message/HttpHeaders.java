package com.vklp.http.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders{

	public enum Headers{
		CONNECTION("Connection"),
		CONTENT_LENGTH("Content-Length"),
		KEEP_ALIVE("Keep-Alive"),
		CONTENT_TYPE("Content-Type"),
		DATE("Date"),
		SERVER("Server"),
		ACCEPT("Accept"),
		LOCATION("Location"),
		CACHE_CONTROL("Cache-Control"),
		ETAG("Etag"),
		IF_NONE_MATCH("If-None-Match");
		
		
		private final String name;
		
		Headers(String name) {
			this.name= name;
		}
		
		public String getName() {
			return name;
		}
	}

	private Map<String, String> headers;
	
	public HttpHeaders() {
		this.headers = new HashMap<String, String>();
	}
	
	public void clear() {
		this.headers.clear();
	}
	
	public HttpHeaders(String name, String value) {
		this.headers.put(name,  value);
	}
	
	public void add(String name, String value) {
		this.headers.put(name,  value);
	}
	
	public void put(String headerLine) {
		String[] items = headerLine.split(":");
		String key = items[0];
		String value = "";
		if(items.length == 2) {
			value = items[1].trim();
		}else {
			for(int i = 1; i<items.length; i++) {
				value += items[i];
			}
		}
		//String[] values = value.trim().split(",");
		add(key, value);
	}
	
	
	
	public void remove(String name) {
		this.headers.remove(name);
	}
	
	public String get(String name) {
		return this.headers.get(name);
	}
	
	public int size() {
		return this.headers.size();
	}
	
	public Set<String> getHeaderNameSet() {
		return this.headers.keySet();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : this.headers.keySet()) {
			sb.append(key + ": " + this.headers.get(key) + "\n");
		}
		return sb.toString().trim();
	}
	
	public boolean contains(String key) {
		return this.headers.containsKey(key);
	}
}
