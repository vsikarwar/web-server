package com.ws.http.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HTTPHeader {
	
	private String name;
	private List<String> values;
	
	public HTTPHeader(String name) {
		values = new ArrayList<String>();
		this.name = name;
	}
	
	public HTTPHeader(String name, String value) {
		this(name);
		this.values.add(value);
	}
	
	public HTTPHeader(String name, String[] values) {
		this(name);
		Collections.addAll(this.values, values);
	}
	
	public String getValue() {
		return this.values.get(0);
	}
	
	public List<String> getValues(){
		return Collections.unmodifiableList(this.values);
	}
	
	public void remove(String value) {
		this.values.remove(value);
	}
	
	public void add(String value) {
		this.values.add(value);
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return this.name + ": " + String.join(",", this.values);
	}

}
