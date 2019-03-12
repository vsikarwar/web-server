package com.vklp.http.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config{
	
	public enum Configs {
		
		PORT("port"),
		SSL_PORT("ssl.port"),
		DEFAULT_THREADS("default.threads"),
		MAX_THREADS("max.threads"),
		SOCKET_BUFFER_SIZE("socket.buff.size"),
		SERVER_NAME("server.name"),
		DOC_ROOT("doc.root"),
		ERROR_400("error.400"),
		ERROR_500("error.500"),
		KEEP_ALIVE_TIMEOUT("keep-alive.timeout"),
		KEEP_ALIVE_MAX("keep-alive.max");
		
		private String conf;
		
		private Configs(String conf) {
			this.conf = conf;
		}
		
		public String config() {
			return this.conf;
		}

	}
	
    private static final Map<String, String> configs = new HashMap<String, String>();
    
    private static final String CONFIG_PATH = "src/main/resources/conf/server.conf";
    
    private static final Config INSTANCE = new Config();
    
    public static Config getInstance() {
    	return INSTANCE;
    }
    
    private Config() {
    	initConfigs();
    }
    
    private void initConfigs() {
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(CONFIG_PATH));
			
			JSONObject jsonObj = (JSONObject) obj;
			
			for(Configs config: Configs.values()) {
				configs.put(config.config(), jsonObj.get(config.config()).toString());
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    public String getStr(String key) {
    	if(!configs.containsKey(key)) {
    		throw new IllegalArgumentException("Config not found!!");
    	}
    	return configs.get(key);
    }
    
    public Integer getInt(String key) {
    	if(!configs.containsKey(key)) {
    		throw new IllegalArgumentException("Config not found!!");
    	}
    	int value = Integer.valueOf(configs.get(key));
    	return value;
    }

}
