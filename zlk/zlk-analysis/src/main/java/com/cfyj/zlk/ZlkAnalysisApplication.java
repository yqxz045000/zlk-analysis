package com.cfyj.zlk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cfyj.zlk")
public class ZlkAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZlkAnalysisApplication.class, args);
	}
}
