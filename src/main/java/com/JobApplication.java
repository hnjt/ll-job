package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.config.property.MyConfig;

@SpringBootApplication
@EnableConfigurationProperties(MyConfig.class)
public class JobApplication {

	public static void main(String[] args) {
		  SpringApplication.run( JobApplication.class, args);
	}
}
