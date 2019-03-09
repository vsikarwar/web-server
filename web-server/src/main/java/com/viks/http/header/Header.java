package com.viks.http.header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Header {
	
	private String name;
	private List<String> values;
	
	public Header(String name) {
		this.name = name;
		this.values = new ArrayList<String>();
	}
	
	public Header(String name, String value) {
		this(name);
		this.values.add(value);
	}
	
	public Header(String name, List<String> values) {
		this(name);
		this.values = values;
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		if(this.values.isEmpty()) {
			return null;
		}
		return this.values.get(0);
	}

	public List<String> getValues() {
		return Collections.unmodifiableList(values);
	}
	
	public void addValue(String value) {
		this.values.add(value);
	}
	
	public void remove(String value) {
		this.values.remove(value);
	}
	
	public void addValues(String[] values) {
		this.values.clear();
		Collections.addAll(this.values, values);
	}
	
	public String toString() {
		return name + ": " + String.join(",", values);
	}

}
