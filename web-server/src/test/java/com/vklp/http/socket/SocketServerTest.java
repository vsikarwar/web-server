package com.vklp.http.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;
import com.vklp.http.server.SocketServer;

public class SocketServerTest {
	
	SocketServer server;
	Config conf = Config.getInstance();

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
