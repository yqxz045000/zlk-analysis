package com.cfyj.zlk.football.analysis.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cfyj.zlk.football.analysis.ArrangementMatchOddsAnalysisTask;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ArrangementMatchOddsAnalysisTaskTest {
	
	@Autowired
	ArrangementMatchOddsAnalysisTask task;
	
	
	@Test
	public void test() throws Exception {
		try {
			task.arrangementMatchOddsAnalysisTask();		
		} catch (Exception e) {
			log.error("",e);
		}
	}

}
