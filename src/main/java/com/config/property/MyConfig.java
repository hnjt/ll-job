package com.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties()
public class MyConfig {


//	# 请求失败的尝试间隔(单位秒)
	@Value("${retry_interval}")
	private int retryInterval;
//	# 最大尝试次数
	@Value("${max_retry_num}")
	private int  maxRetryNum;
	//请求超时时间
	@Value("${time_out}")
	private int timeOut;
	public int getRetryInterval() {
		return retryInterval;
	}
	public void setRetryInterval(int retryInterval) {
		this.retryInterval = retryInterval;
	}
	public int getMaxRetryNum() {
		return maxRetryNum;
	}
	public void setMaxRetryNum(int maxRetryNum) {
		this.maxRetryNum = maxRetryNum;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}




}
