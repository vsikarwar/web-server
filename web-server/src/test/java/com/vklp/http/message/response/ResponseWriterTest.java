package com.vklp.http.message.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.HttpVersion;

public class ResponseWriterTest {

	ResponseWriter writer;
	OutputStream out;
	
	@Before
	public void setUp() throws Exception {
		out = new OutputStream(){
	        private StringBuilder string = new StringBuilder();
	        @Override
	        public void write(int b) throws IOException {
	            this.string.append((char) b );
	        }

	        public String toString(){
	            return this.string.toString();
	        }
	    };
		writer = new ResponseWriter(out);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOutputStream() {
		HttpResponse response = new HttpResponse();
		response.setVersion(new HttpVersion(1, 1));
		response.setStatus(HttpStatus.OK);
		
		response.addHeader(Headers.CONNECTION, "keep-alive");
		response.addHeader(Headers.SERVER, "test-server");
		
		writer.write(response);
		
		String expectedResult = "HTTP/1.1 200 OK\n" + 
				"Server: test-server\n" + 
				"Connection: keep-alive";
		
		assertEquals(out.toString().trim(), expectedResult);
	}

}
