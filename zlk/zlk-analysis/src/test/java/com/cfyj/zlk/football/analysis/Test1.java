package com.cfyj.zlk.football.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cfyj.zlk.football.analysis.example.converter.DataConverter;
import com.cfyj.zlk.football.analysis.example.service.ExampleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

	@Autowired
	private ExampleService exampleService;
	
	@Autowired
	private DataConverter dataConverter;
	
	@Test
	public void test1() throws Exception {
		exampleService.analysisSPF2();
	}
	
	@Test
	public void test2() throws Exception {
		dataConverter.oddsConverter();
	}
}
