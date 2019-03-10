package com.viks.http.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.server.ServerConfig;
import com.viks.http.server.ServerConfig.ServerConfigs;

public class SocketServerTest {
	
	SocketServer server;
	ServerConfig conf = ServerConfig.getInstance();

	@Before
	public void setUp() throws Exception {
		server = new SocketServer(this.conf.getInt(ServerConfigs.PORT.config()), 
				this.conf.getInt(ServerConfigs.DEFAULT_THREADS.config()), 
				this.conf.getInt(ServerConfigs.MAX_THREADS.config()), 
				this.conf.getInt(ServerConfigs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(ServerConfigs.SERVER_NAME.config()));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		server.start();
		
	}

}
