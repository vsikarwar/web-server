package com.vklp.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.vklp.http.session.ServerSession;

public class AbstractSocketServer extends Thread{
	
	private static final Logger logger = Logger.getLogger(AbstractSocketServer.class);
	
	protected final ThreadPoolExecutor threadPool;
    protected final int port;
    protected final ThreadGroup threadGroup;
    protected final int socketBufferSize;
    protected final int maxThreads;
    protected final AtomicLong sessionIdSequence;
    protected final ConcurrentMap<Long, ServerSession> activeSessions;
    protected final String serverName;
    
    protected ServerSocket serverSocket = null;
    
    public AbstractSocketServer(int port,
            int defaultThreads,
            int maxThreads,
            int socketBufferSize,
            String serverName) {
    	this.port = port;
        this.socketBufferSize = socketBufferSize;
        this.threadGroup = new ThreadGroup("socket-server");
        this.maxThreads = maxThreads;
        this.threadPool = new ThreadPoolExecutor(defaultThreads,
                maxThreads,
                0,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory,
                rejectedExecutionHandler);
        
        this.sessionIdSequence = new AtomicLong(1);
        this.activeSessions = new ConcurrentHashMap<Long, ServerSession>();
        this.serverName = serverName;
    }
    
    private final ThreadFactory threadFactory = new ThreadFactory() {
		private AtomicLong threadId = new AtomicLong(0);

		public Thread newThread(Runnable r) {
			String name = "web-server-" + threadId.getAndIncrement();
			Thread t = new Thread(threadGroup, r, name);
			return t;
		}
	};
	
	private final RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			ServerSession session = (ServerSession) r;
			
			logger.error("Too many open connections " +
								executor.getActiveCount() + " of " +
								executor.getLargestPoolSize() + " threads are in use. " + 
								session.getSocket().getRemoteSocketAddress());
		}
	};
	
	public void shutdown() {
        logger.error("Shutting down socket server.");

        // first shut down the acceptor to stop new connections
        interrupt();
        try {
            if(!serverSocket.isClosed())
                serverSocket.close();
        } catch(IOException e) {
            logger.error("Error while closing socket server: " + e.getMessage());
        }

        // now kill all the active sessions
        threadPool.shutdownNow();
        killActiveSessions();

        try {
            boolean completed = threadPool.awaitTermination(5, TimeUnit.SECONDS);
            if(!completed) {}
                logger.warn("Timed out waiting for threadpool to close.");
        } catch(InterruptedException e) {
            logger.warn("Interrupted while waiting for socket server shutdown to complete: ", e);
        }
    }
	
	public void killActiveSessions() {
        //loop through and close the socket of all the active sessions
        logger.info("Killing all active sessions.");
        for(Map.Entry<Long, ServerSession> entry: activeSessions.entrySet()) {
            try {
                logger.debug("Closing session " + entry.getKey());
                entry.getValue().close();
            } catch(IOException e) {
                logger.warn("Error while closing session socket: ", e);
            }
        }
    }


}
