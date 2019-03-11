package com.viks.http.main.service;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractService implements MainService{
	
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
		
		System.out.println("Starting the server!!");
		
		startInnerService();
	}

	public void stop() {
		System.out.println("Stopping the server");
		synchronized(this) {
            if(!isStarted()) {
                System.out.println("The service is already stopped, ignoring duplicate attempt.");
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
