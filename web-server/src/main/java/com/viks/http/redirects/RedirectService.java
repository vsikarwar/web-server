package com.viks.http.redirects;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RedirectService {
	
	public void readRedirects() {
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader("src/main/resources/conf/redirect.json"));
			JSONArray jsonArray = (JSONArray) obj;
			
			for(int i = 0; i<jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				String source = jsonObject.get("source").toString();
				String destination = jsonObject.get("destination").toString();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
