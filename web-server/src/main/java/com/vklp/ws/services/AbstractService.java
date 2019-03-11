package com.vklp.ws.services;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.vklp.http.config.Config;

public abstract class AbstractService implements HTTPService{
	
	private static final Logger logger = Logger.getLogger(AbstractService.class);
	
    private final AtomicBoolean isStarted;
    private String type;
    

    public AbstractService() {
    	this.isStarted = new AtomicBoolean(false);
    }
    
    public AbstractService(String type) {
    	this();
    	this.type = type;
    }
    
    public String getType() {
    	return this.type;
    }

	public void start() {
		boolean isServerStarted = isStarted.compareAndSet(false, true);
		if(!isServerStarted)
            throw new IllegalStateException("Server is already started!");
		
		logger.debug("Starting the server!!");
		
		startInnerService();
	}

	public void stop() {
		logger.debug("Stopping the server");
		synchronized(this) {
            if(!isStarted()) {
            	logger.debug("The service is already stopped, ignoring duplicate attempt.");
                return;
            }

            stopInnerService();
            isStarted.set(false);
        }
	}

	public boolean isStarted() {
		return this.isStarted.get();
	}

	
	protected abstract void startInnerService();

    protected abstract void stopInnerService();
}
