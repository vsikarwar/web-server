package com.vklp.http.message.response;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpStatusTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		assertEquals(HttpStatus.OK.code(), 200);
		assertEquals(HttpStatus.OK.description(), "OK");
		
		assertEquals(HttpStatus.NOT_FOUND.code(), 404);
		assertEquals(HttpStatus.NOT_FOUND.description(), "Not Found");
		
	}

}
