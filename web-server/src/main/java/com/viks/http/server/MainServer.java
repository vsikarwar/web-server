package com.viks.http.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;
import com.viks.http.main.service.AbstractService;
import com.viks.http.main.service.MainService;
import com.viks.http.socket.SocketService;
import com.viks.http.ssl.SSLService;

public class MainServer extends AbstractService{
	
	private final static Logger logger = Logger.getLogger(MainServer.class);
	
    private final List<MainService> basicServices;
    private final static ConfigService config = ConfigService.getInstance();
    
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
		
		logger.info("Startup completed in " + (end - start) + " ms.");
		
	}

	@Override
	protected void stopInnerService() {
		System.out.println("Stopping inner services");
		
		for(int i = basicServices.size()-1; i>0; i--) {
			basicServices.get(i).stop();
		}
		
	}
	
	private List<MainService> createBasicServices(){
        List<MainService> services = new ArrayList<MainService>();
        
        //TODO: add jmx service
        
        //Socket service
        SocketService socketService = new SocketService(config);
        services.add(socketService);
        
        //SSL service
        SSLService sslService = new SSLService();
        services.add(sslService);
        
        //TODO: add web socket service
        
        return Collections.unmodifiableList(services);

	}
	
	public static  void main(String[] args) {
		String serverName = config.getStr(Configs.SERVER_NAME.config());
		logger.info("Starting server .. " + serverName);
		
        final MainServer server = new MainServer();
        
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
