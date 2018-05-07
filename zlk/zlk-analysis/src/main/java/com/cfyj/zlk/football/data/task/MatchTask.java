package com.cfyj.zlk.football.data.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.data.spider.FinshMatchSpider;
import com.cfyj.zlk.football.data.spider.FutureMatchSpider;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MatchTask {
	@Autowired
	private FutureMatchSpider futureMatchSpider;
	
	@Autowired
	private FinshMatchSpider finshMatchSpider;
	
	@Scheduled(cron = "0 0 3,13,22 * * ? ")
//	@Scheduled(fixedDelay = 100000000)
	public void futureMatchSpider() throws Exception {
		log.info("爬取未来赛程---start");
		futureMatchSpider.spiderData();
	}
	
//	@Scheduled(cron= "0 0 0,6,11,17,19,22 * * ? ")
	@Scheduled(fixedDelay = 100000000)
	public void finshMatchSpider() throws Exception {
		log.info("爬取完赛赛果---start");
		finshMatchSpider.spiderData();
	}
}
