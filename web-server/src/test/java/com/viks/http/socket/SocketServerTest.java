package com.viks.http.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;

public class SocketServerTest {
	
	SocketServer server;
	ConfigService conf = ConfigService.getInstance();

	@Before
	public void setUp() throws Exception {
		server = new SocketServer(this.conf.getInt(Configs.PORT.config()), 
				this.conf.getInt(Configs.DEFAULT_THREADS.config()), 
				this.conf.getInt(Configs.MAX_THREADS.config()), 
				this.conf.getInt(Configs.SOCKET_BUFFER_SIZE.config()), 
				this.conf.getStr(Configs.SERVER_NAME.config()));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		server.start();
		
	}

}
