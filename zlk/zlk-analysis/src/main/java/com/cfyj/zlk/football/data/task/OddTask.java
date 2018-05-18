package com.cfyj.zlk.football.data.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.data.spider.OuOddsSpider;
import com.cfyj.zlk.football.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OddTask {
	
//	@Autowired
//	private HisdOuOddsSpider hisdOuOddsSpider;
//
//	@Scheduled(fixedDelay= 100000000)
//	public void finshMatchSpider() throws Exception {
//		Date begin = new Date();
//		log.info("爬取历史赔率开始---start");
//		hisdOuOddsSpider.spiderData();
//		Date end = new Date();
//		log.info("爬取历史赔率结束---begin :{},end:{}",DateUtil.dateToLong2DateStr(begin),DateUtil.dateToLong2DateStr(end));
//	}
	
	@Autowired
	OuOddsSpider ouOddsSpider;
	
	@Scheduled(fixedDelay=1800000,initialDelay=180000)
	public void finshMatchSpider() throws Exception {
		Date begin = new Date();
		log.info("爬取赔率开始---start");
		ouOddsSpider.spiderData();
		Date end = new Date();
		log.info("爬取赔率结束---begin :{},end:{}",DateUtil.dateToLong2DateStr(begin),DateUtil.dateToLong2DateStr(end));
	}
	
}
