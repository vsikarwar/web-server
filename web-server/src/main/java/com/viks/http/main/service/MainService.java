package com.viks.http.main.service;

public interface MainService {
	
	/**
     * Start the service.
     */
    public void start();

    /**
     * Stop the service
     */
    public void stop();

    /**
     * @return true iff the service is started
     */
    public boolean isStarted();
    

}
