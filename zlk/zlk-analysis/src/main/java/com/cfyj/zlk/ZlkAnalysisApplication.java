package com.cfyj.zlk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
//@ComponentScan("com.cfyj.zlk")
//com.cfyj.zlk.football.data.task
@ComponentScan(basePackages = "com.cfyj.zlk"
//,excludeFilters = {@Filter(type=FilterType.REGEX,pattern = {"com\\.cfyj\\.zlk\\.football\\.data\\.task\\.[\\s\\S]*"})}
)
public class ZlkAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZlkAnalysisApplication.class, args);
	}
}
