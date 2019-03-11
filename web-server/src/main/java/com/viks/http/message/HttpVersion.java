package com.viks.http.message;

import java.util.ArrayList;
import java.util.List;

public class HttpVersion implements Comparable<HttpVersion>{
	
	private static final List<String> supportedVersions = new ArrayList<String>();
	
	static {
		supportedVersions.add("HTTP/1.0");
		supportedVersions.add("HTTP/1.1");
	}
	
	private int minorVersion;
	private int majorVersion;
	
	public HttpVersion() {}
	
	public HttpVersion(int minor, int major) {
		this.minorVersion = minor;
		this.majorVersion = major;
	}
	
	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}
	
	public boolean isValid() {
		if(supportedVersions.contains(this.toString())) {
			return true;
		}
		return false;
	}

	public int compareTo(HttpVersion o) {
		if(this.getMajorVersion() == o.getMajorVersion()) {
			if(this.getMinorVersion() == o.getMinorVersion()) {
				return 0;
			}else {
				return this.getMinorVersion() > o.getMinorVersion() ? 1 : -1;
			}
		}else {
			return this.getMajorVersion() > o.getMajorVersion() ? 1 : -1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + majorVersion;
		result = prime * result + minorVersion;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpVersion other = (HttpVersion) obj;
		if (majorVersion != other.majorVersion)
			return false;
		if (minorVersion != other.minorVersion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HTTP/" + this.getMajorVersion() + "." + this.getMinorVersion();
	}
	
	

}
