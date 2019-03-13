package com.vklp.http.ITests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpVersion;
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
import com.vklp.ws.services.SocketService;

public class HTTPKeepAliveTest {
	
	SocketService service;
	
	private static final String USER_AGENT = "Mozilla/5.0";
	private Config config = Config.getInstance();
	private RequestHandler handler = new RequestHandler();

	@Before
	public void setUp() {
		service = new SocketService(config, handler);
		service.start();
}
	
	@After
	public void tearDown() throws SocketException {
			//Thread.sleep(2000);
		thrown.expect(SocketException.class);
		service.stop();
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	
	@Test(expected = IOException.class)
	public void testSocketServiceGet() throws ClientProtocolException, IOException {
		URL url = new URL("http://localhost:8000/index.html");
		URLConnection uc = url.openConnection();
		uc.getOutputStream()
		uc.setRequestProperty("Connection", "keep-alive");
		uc.connect();
		
		BufferedReader reader = new BufferedReader( 
		          new InputStreamReader(uc.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		
		//sendGETOK("http://localhost:8000/index.html");
		
		thrown.expect(ClientProtocolException.class);
		thrown.expect(IOException.class);
		thrown.expect(SocketException.class);
		
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
	
	/* keep-alive is not enabled for version http1.0
	 * if header does not contain Connection: keep-alive then connection will not remain open and second request should fail.
	 */
	private static void sendGETOK(String getUrl) throws ClientProtocolException, IOException  {
		
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(getUrl);
			httpGet.addHeader("User-Agent", USER_AGENT);
			httpGet.setProtocolVersion(HttpVersion.HTTP_1_1);
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			assertEquals(200, httpResponse.getStatusLine().getStatusCode());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			
			
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			
			for(int i = 0; i<10; i++) {
				System.out.println("*****requesting : " + i);
				httpResponse = httpClient.execute(httpGet);
				assertEquals(200, httpResponse.getStatusLine().getStatusCode());
				httpResponse.
				reader = new BufferedReader(new InputStreamReader(
						httpResponse.getEntity().getContent()));
				inputLine = "";
				response = new StringBuffer();
		
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				
				System.out.println(response.toString());
			}
			
			reader.close();
			
	
			// print result
			//assertEquals(readFile("./content/index.html"), response.toString());
			httpClient.close();
	}

}
