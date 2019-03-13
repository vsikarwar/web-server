package com.vklp.http.handlers.basic;

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

import com.vklp.http.handlers.core.GETHandler;
import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;
import com.vklp.http.redirects.Redirect;

public class RedirectHandler extends GETHandler{
	
	private static final Map<String, Redirect> redirects = new HashMap<String, Redirect>();
	
	private static final Logger logger = Logger.getLogger(RedirectHandler.class);
	
	public RedirectHandler() {
		initRedirects();
	}
	
	public boolean hasRedirects() {
		return !redirects.isEmpty();
	}
	
	public boolean contains(String path) {
		return redirects.containsKey(path);
	}
	
	private Redirect getRedirect(String path) {
		return redirects.get(path);
	}
	
	private void initRedirects() {
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader("src/main/resources/conf/redirect.json"));
			JSONArray jsonArray = (JSONArray) obj;
			
			for(int i = 0; i<jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				String source = jsonObject.get("source").toString();
				String destination = jsonObject.get("destination").toString();
				String type = jsonObject.get("type").toString();
				
				logger.debug("Reading Redirect.. " + " " + source + " " + destination + " " + type);
				
				Redirect redirect = new Redirect(source, destination, type);
				redirects.put(source, redirect);
			}
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		
	}

	public boolean canHandle(HttpRequest req, HttpResponse res) {
		return this.hasRedirects() && this.contains(req.getUri());
	}

	public void doGet(HttpRequest req, HttpResponse res) {
		if(this.hasRedirects() && this.contains(req.getUri())) {
			Redirect redirect = this.getRedirect(req.getUri());
			if(redirect.getType().equals("301")) {
				res.setStatus(HttpStatus.MOVED_PERMANENTLY);
			}else {
				res.setStatus(HttpStatus.MOVED_TEMPORARILY);
			}
			res.addHeader(Headers.LOCATION.getName(), redirect.getDestination());
		}
	}
	
}
