package com.vklp.http.services;

public interface HTTPService {
	
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
