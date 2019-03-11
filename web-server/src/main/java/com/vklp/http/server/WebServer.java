package com.vklp.http.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.vklp.ws.services.AbstractService;
import com.vklp.ws.services.HTTPService;
import com.vklp.ws.services.JMXService;
import com.vklp.ws.services.SSLService;
import com.vklp.ws.services.SocketService;
import com.vklp.ws.services.WebSocketService;

public class WebServer extends AbstractService{
	
	private final static Logger logger = Logger.getLogger(WebServer.class);
	
    private final List<HTTPService> basicServices;
    
    public WebServer() {
    	basicServices = createBasicServices();
    }


	@Override
	protected void startInnerService() {
		
		long start = System.currentTimeMillis();
		
		for(HTTPService service : basicServices) {
			service.start();
		}
		
		long end = System.currentTimeMillis();
		
		logger.info("Startup completed in " + (end - start) + " ms.");
		
	}

	@Override
	protected void stopInnerService() {
		System.out.println("Stopping inner services");
		
		for(int i = basicServices.size()-1; i>0; i--) {
			basicServices.get(i).stop();
		}
		
	}
	
	private List<HTTPService> createBasicServices(){
        List<HTTPService> services = new ArrayList<HTTPService>();
        
        //TODO: add jmx service
        services.add(new JMXService());
        
        //Socket service
        services.add(new SocketService());
        
        //SSL service
        services.add(new SSLService());
        
        //TODO: add web socket service
        services.add(new WebSocketService());
        
        return Collections.unmodifiableList(services);
	}
	
	public static  void main(String[] args) {
		logger.info("Starting server .. ");
		
        final WebServer server = new WebServer();
        
        if(!server.isStarted())
            server.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (server.isStarted())
                    server.stop();
            }
        });
		
	}

}
