package com.viks.http.header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders{

	class HeaderMap extends HashMap<String, List<String>>{
		private static final long serialVersionUID = 1L;

		@Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for(Object key: this.keySet()) {
                builder.append(key.toString() + ": " + String.join(",", this.get(key)) + "\n");
            }
            return builder.toString();
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
	
	public void add(String name, String[] values) {
		this.add(name);
		Collections.addAll(this.headers.get(name), values);
	}
	
	public void remove(String name, String value) {
		this.headers.get(name).remove(value);
	}
}
