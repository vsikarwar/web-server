package com.vklp.http.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.config.Config;
import com.vklp.http.config.Config.Configs;

public class ServerConfigTest {
	
	Config config;

	@Before
	public void setUp() throws Exception {
		config = Config.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet() {
		Integer port = config.getInt(Configs.PORT.config());
		assertNotNull(port);
		
		String serverName = config.getStr(Configs.SERVER_NAME.config());
		assertEquals(serverName, "WS-0.1");
	}

}
