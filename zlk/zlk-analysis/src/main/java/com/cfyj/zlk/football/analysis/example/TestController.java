package com.cfyj.zlk.football.analysis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cfyj.zlk.football.analysis.example.service.ExampleService;

@RestController
public class TestController {
	
	
	@Autowired
	private ExampleService exampleService	;
	
	@RequestMapping("test1")
	public void test1() throws Exception {
		exampleService.analysisSPF2();
	}

	
	
	
	
	
}
