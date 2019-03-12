package com.vklp.http.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.vklp.http.message.HttpHeaders.Headers;
import com.vklp.http.message.request.HttpRequest;
import com.vklp.http.message.response.HttpResponse;
import com.vklp.http.message.response.HttpStatus;

public class ETagHandler extends GETHandler{
	
	public void doGet(HttpRequest req, HttpResponse res) {
		notModified(req, res);
	}
	
	public boolean canHandle(HttpRequest req, HttpResponse res) {
		return req.getHeader(Headers.ETAG) != null;
	}

	private void notModified(HttpRequest req, HttpResponse res) {
		if(req.getHeader(Headers.IF_NONE_MATCH)!=null) {
			try {
				String checksum = fileService.getChecksum(req.getUri());
				if(checksum.equals(req.getHeader(Headers.IF_NONE_MATCH))) {
					res.addHeader(Headers.CACHE_CONTROL, "max-age=120");
					res.addHeader(Headers.ETAG, checksum);
					res.setStatus(HttpStatus.NOT_MODIFIED);
				}
			} catch (FileNotFoundException e) {
				handleError(res, HttpStatus.NOT_FOUND);
				e.printStackTrace();
			} catch (IOException e) {
				handleError(res, HttpStatus.INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
			
		}
	}

}
