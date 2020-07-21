package com.utils;

public class ADServerInfo {
	private String ipAddr;
	private String port;
	private String domainName;

	public ADServerInfo() {
	}

	public ADServerInfo(String ipAddr, String port, String domainName) {
		this.ipAddr = ipAddr;
		this.port = port;
		this.domainName = domainName;
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}