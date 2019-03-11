package com.viks.http.redirects;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class RedirectService {
	
	private static final Map<String, Redirect> redirects = new HashMap<String, Redirect>();
	
	private static final RedirectService INSTANCE = new RedirectService();
	
	//private static final Logger logger = Logger.getLogger(RedirectService.class);
	
	private RedirectService() {
		readRedirects();
	}
	
	public static RedirectService getInstance() {
		 return INSTANCE;
	}
	
	public boolean hasRedirects() {
		return !redirects.isEmpty();
	}
	
	public boolean contains(String path) {
		return redirects.containsKey(path);
	}
	
	public Redirect getRedirect(String path) {
		return redirects.get(path);
	}
	
	private void readRedirects() {
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader("src/main/resources/conf/redirect.json"));
			JSONArray jsonArray = (JSONArray) obj;
			
			for(int i = 0; i<jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				String source = jsonObject.get("source").toString();
				String destination = jsonObject.get("destination").toString();
				String type = jsonObject.get("type").toString();
				
				System.out.println("Reading Redirect.. ");
				System.out.println(" " + source + " " + destination + " " + type);
				
				Redirect redirect = new Redirect(source, destination, type);
				redirects.put(source, redirect);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
