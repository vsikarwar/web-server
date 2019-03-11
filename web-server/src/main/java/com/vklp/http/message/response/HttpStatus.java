package com.vklp.http.message.response;

public enum HttpStatus {
	
	//Informational Response
	CONTINUE_100(100, "Continue"),
	CONTINUE_101(101, "Switching Protocol"),
	PROCESSING(102, "Processing"),
	EARLY_HINT(103, "Early Hint"),
	//Success
	OK(200, "OK"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
	NO_CONTENT(204, "No Content"),
	RESET_CONTENT(205, "Reset Content"),
	PARTIAL_CONTENT(206, "Partial Content"),
	MULTI_STATUS(207, "Multi Status"),
	ALREADY_REPORTED(208, "Already Reported"),
	//Redirection
	MULTIPLE_CHOICES(300, "Multiple Choices"),
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	MOVED_TEMPORARILY(302, "Moved Temporarily"),
	SEE_OTHER(303, "See Other"),
	NOT_MODIFIED(304, "Not Modified"),
	USE_PROXY(305, "Use Proxy"),
	SWITCH_PROXY(306, "Switch Proxy"),
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	PERMANENT_REDIRECT(308, "Permanent Redirect"),
	//Client Errors
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	PAYMENT_REQUIRED(402, "Payment Required"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	REQUEST_TIMEOUT(408, "Request Timeout"),
	CONFLICT(409, "Conflict"),
	GONE(410, "Gone"),
	LENGTH_REQUIRED(411, "Length Required"),
	PRECONDITION_FAILED(412, "Precondition Failed"),
	PAYLOAD_TO_LARGE(413, "Payload To Large"),
	URI_TO_LONG(414, "URI To Long"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	//Server Errors
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NOT_IMPLEMENTED(501, "Not Implemented"),
	BAD_GATEWAY(502, "Bad Gateway"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	HTTP_VERSION_NOT_SUPPORTED(505, "Http Version not Supported");
	
	
	private final int code;
	private final String description; 
	
	
	HttpStatus(int code, String description){
		this.code = code;
		this.description = description;
	}
	
	public static HttpStatus getStatus(int code) {
		for(HttpStatus status : HttpStatus.values()) {
			if(status.code() == code) {
				return status;
			}
		}
		
		throw new IllegalArgumentException("Unsupported status code: " + code);
	}

	
	public int code() {
		return code;
	}
	
	public String description() {
		return description;
	}
	
	public String toString() {
		return this.code + " " + this.description;
	}
}
