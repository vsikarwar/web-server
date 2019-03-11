package com.viks.http.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders{

	public enum Headers{
		CONNECTION("Connection"),
		CONTENT_LENGTH("Content-Length"),
		KEEP_ALIVE("Keep-Alive"),
		CONTENT_TYPE("Content-type"),
		DATE("Date"),
		SERVER("Server"),
		ACCEPT("Accept"),
		LOCATION("Location");
		
		private final String name;
		
		Headers(String name) {
			this.name= name;
		}
		
		public String getName() {
			return name;
		}
	}

	class HeaderMap extends HashMap<String, List<String>>{
		private static final long serialVersionUID = 1L;

		@Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for(Object key: this.keySet()) {
                builder.append(key.toString() + ": " + String.join(",", this.get(key)) + "\n");
            }
            return builder.toString().trim();
        }
	};
	
	private Map<String, List<String>> headers;
	
	public HttpHeaders() {
		this.headers = new HeaderMap();
	}
	
	public HttpHeaders(String name) {
		this();
		this.headers.put(name, new ArrayList<String>());
	}
	
	public HttpHeaders(String name, String value) {
		this(name);
		this.headers.get(name).add(value);
	}
	
	public HttpHeaders(String name, String[] values) {
		this(name);
		Collections.addAll(this.headers.get(name), values);
	}
	
	public void add(String name) {
		if(!this.headers.containsKey(name)) {
			this.headers.put(name, new ArrayList<String>());
		}
	}
	
	public void add(String name, String value) {
		this.add(name);
		this.headers.get(name).add(value);
	}
	
	public void put(String headerLine) {
		String[] items = headerLine.split(":");
		String key = items[0];
		String value = "";
		if(items.length == 2) {
			value = items[1];
		}else {
			for(int i = 1; i<items.length; i++) {
				value += items[i];
			}
		}
		String[] values = value.trim().split(",");
		add(key, values);
	}
	
	public void add(String name, String[] values) {
		this.add(name);
		Collections.addAll(this.headers.get(name), values);
	}
	
	public void remove(String name, String value) {
		this.headers.get(name).remove(value);
	}
	
	public String get(String name) {
		return this.headers.get(name).get(0);
	}
	
	public List<String> getAll(String name) {
		return this.headers.get(name);
	}
	
	public String toString() {
		return this.headers.toString();
	}
}
