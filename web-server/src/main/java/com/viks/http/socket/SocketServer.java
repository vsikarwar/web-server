package com.viks.http.socket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.viks.http.request.RequestHandler;


public class SocketServer extends Thread{
	
    private final ThreadPoolExecutor threadPool;
    private final int port;
    private final ThreadGroup threadGroup;
    private final int socketBufferSize;
    private final int maxThreads;
    //private final StatusManager statusManager;
    private final AtomicLong sessionIdSequence;
    private final ConcurrentMap<Long, SocketServerSession> activeSessions;
    private final String serverName;

    private ServerSocket serverSocket = null;

	
	public SocketServer(int port,
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
        
        this.sessionIdSequence = new AtomicLong(0);
        this.activeSessions = new ConcurrentHashMap<Long, SocketServerSession>();
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
			SocketServerSession session = (SocketServerSession) r;
			
			System.out.println("Too many open connections " +
								executor.getActiveCount() + " of " +
								executor.getLargestPoolSize() + " threads are in use. " + 
								session.getSocket().getRemoteSocketAddress());
			
		}
		
	};
	
	public void run() {
		System.out.println("Starting socket server on port " + port);
		
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(port));
			serverSocket.setReceiveBufferSize(this.socketBufferSize);
			
			while(!isInterrupted() && !serverSocket.isClosed()) {
				System.out.println("Socket accepting connections : ");
				final Socket socket = serverSocket.accept();
				System.out.println("Socket accepting connections : " + socket.getRemoteSocketAddress());
				//configure socket
				long sessionId = this.sessionIdSequence.getAndIncrement();
				this.threadPool.execute(new SocketServerSession(activeSessions,
                        socket,
                        sessionId));
				System.out.println("POOL SIZE : " + threadPool.getPoolSize());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
