package com.cfyj.zlk.football.analysis.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cfyj.zlk.football.data.spider.OuOddsSpider;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OuOddsSpiderTest {
	
	@Autowired
	private OuOddsSpider ouOddsSpider;
	
	@Test
	public void spider() throws Exception {
		ouOddsSpider.spiderData();
	}
}
