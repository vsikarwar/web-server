package com.vklp.http.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.HandlerRegistry;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.services.AbstractService;
import com.vklp.http.services.HTTPService;
import com.vklp.http.services.JMXService;
import com.vklp.http.services.SSLService;
import com.vklp.http.services.SocketService;
import com.vklp.http.services.WebSocketService;
import com.vklp.http.storage.StorageServiceFactory;

public class WebServer extends AbstractService{
	
	private final static Logger logger = Logger.getLogger(WebServer.class);
	
    private final List<HTTPService> basicServices;
    
    private final Config conf;
    
    private final RequestHandler handler;
    
    private final StorageServiceFactory storageServiceFactory;
    
    public WebServer() {
    	this.conf = Config.getInstance();
    	this.handler = HandlerRegistry.getInstance().getHandler();
    	this.storageServiceFactory = StorageServiceFactory.getInstance();
    	
    	this.basicServices = createBasicServices();
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
		logger.info("Stopping inner services");
		
		for(int i = basicServices.size()-1; i>0; i--) {
			basicServices.get(i).stop();
		}
		
	}
	
	private List<HTTPService> createBasicServices(){
        List<HTTPService> services = new ArrayList<HTTPService>();
        
        //TODO: add jmx service
        services.add(new JMXService());
        
        //Socket service
        services.add(new SocketService(conf, handler));
        
        //SSL service
        services.add(new SSLService(conf, handler));
        
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
