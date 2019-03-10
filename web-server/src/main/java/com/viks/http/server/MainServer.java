package com.viks.http.server;

import java.util.ArrayList;
import java.util.List;

import com.viks.http.main.service.AbstractService;
import com.viks.http.main.service.MainService;
import com.viks.http.socket.SocketService;

public class MainServer extends AbstractService{
	
	//TODO: add loggers
	
    private final List<MainService> basicServices;
    private final ServerConfig configs = ServerConfig.getInstance();
    
    public MainServer() {
    	basicServices = createBasicServices();
    }


	@Override
	protected void startInnerService() {
		long start = System.currentTimeMillis();
		
		for(MainService service : basicServices) {
			service.start();
		}
		
		
		long end = System.currentTimeMillis();
		
		System.out.println("Startup completed in " + (end - start) + " ms.");
		
		//HTTPServer.startServer();
		
	}

	@Override
	protected void stopInnerService() {
		// TODO Auto-generated method stub
		System.out.println("Stopping inner services");
		
	}
	
	
	private List<MainService> createBasicServices(){
        List<MainService> services = new ArrayList<MainService>();
        
        SocketService socketService = new SocketService(configs);
        services.add(socketService);
        
        //add socket service
        //add jmx service
        // add File handler service
        
        //TODO: create immutable list
        return services;

	}
	
	public static  void main(String[] args) {
		//TODO: load config
		
		//Map config = ServerConfig.defaultConfig;
		
        final MainServer server = new MainServer();
        
        if(!server.isStarted())
            server.start();
        
        // add a shutdown hook to stop the server
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                if (server.isStarted())
                    server.stop();
            }
        });

		
		
	}

}
