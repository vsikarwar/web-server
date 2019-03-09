package com.viks.http.handlers;

import com.viks.http.request.Request;
import com.viks.http.response.Response;

public interface RequestHandler {

	void handle(Request request, Response response);
}
