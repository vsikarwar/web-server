package com.viks.http.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServerConfig implements Serializable{
	
    private static final long serialVersionUID = 1;

    //config keys
    public static final String SOCKET_PORT = "socket.port";
    
    private static final Map<String, String> defaultConfig = new HashMap<String, String>();
    
    
    
    static {
    	defaultConfig.put(SOCKET_PORT, "8080");
    }
    
    

}
