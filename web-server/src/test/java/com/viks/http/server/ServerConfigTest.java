package com.viks.http.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viks.http.config.ConfigService;
import com.viks.http.config.ConfigService.Configs;

public class ServerConfigTest {
	
	ConfigService config;

	@Before
	public void setUp() throws Exception {
		config = ConfigService.getInstance();
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
