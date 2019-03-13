package com.vklp.http.ITests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.vklp.http.config.Config;
import com.vklp.http.handlers.RequestHandler;
import com.vklp.http.services.SocketService;

public class HTTPGetOKTest {
	
	SocketService service;
	
	private static final String USER_AGENT = "Mozilla/5.0";
	private Config config = Config.getInstance();
	private RequestHandler handler = new RequestHandler();

	@Before
	public void setUp() {
		try {
			service = new SocketService(config, handler);
			service.start();
		}catch(Exception e) {
			
		}
	}
	
	@After
	public void tearDown()  {
			service.stop();
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testSocketServiceGet() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/index.html";
		
		//thrown.expect(ClientProtocolException.class);
		//thrown.expect(IOException.class);
		
		sendGETOK(getUrl);
	}
	
	
	
	private static String readFile(String fileName) {
		String content=null;
		
		try {
			content = FileUtils.readFileToString(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	private static void sendGETOK(String getUrl) throws ClientProtocolException, IOException  {
		
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(getUrl);
			httpGet.addHeader("User-Agent", USER_AGENT);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			
			assertEquals(200, httpResponse.getStatusLine().getStatusCode());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
	
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
	
			// print result
			System.out.println(response.toString());
			assertEquals(readFile("./content/index.html"), response.toString());
			httpClient.close();
	}

}
