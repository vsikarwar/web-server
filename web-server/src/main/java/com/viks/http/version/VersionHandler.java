package com.viks.http.version;

import java.util.ArrayList;
import java.util.List;

public class VersionHandler {
	
	private static final List<HttpVersion> supportedVersions = new ArrayList<HttpVersion>();
	
	static {
		supportedVersions.add(new HttpVersion(1, 0));
		supportedVersions.add(new HttpVersion(1, 1));
	}
	
	public VersionHandler() {
		
	}
	
	public HttpVersion handle(String versionStr) {
		return parseVersionString(versionStr);
	}
	
	public HttpVersion parseVersionString(String versionStr) {
		HttpVersion httpVersion = null;
		
		if(versionStr.indexOf("/") == -1) {
			throw new IllegalArgumentException("Invalid Http Version: " + versionStr);
		}
		
		String[] splitVersion = versionStr.split("/");
		if(!splitVersion[0].equals("HTTP")) {
			throw new IllegalArgumentException("Invalid Http Version: " + versionStr);
		}
		
		if(splitVersion[1].indexOf('.') == -1) {
			throw new IllegalArgumentException("Invalid Http Version: " + versionStr);
		}
		
		try {
			String[] versions = splitVersion[1].split(".");
			int majorVersion = Integer.parseInt(versions[0]);
			int minorVersion = Integer.parseInt(versions[1]);
			httpVersion = new HttpVersion(majorVersion, minorVersion);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Invalid Http Version: " + versionStr);
		}
		
		return httpVersion;
	}
	
	public boolean isVersionValid(HttpVersion httpVersion) {
		if(supportedVersions.contains(httpVersion)) {
			return true;
		}
		return false;
	}

}
