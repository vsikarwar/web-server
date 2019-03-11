package com.vklp.http.processors;

import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;

public interface Processor {
	
	public void process(HttpRequest req, HttpResponse res);

}
