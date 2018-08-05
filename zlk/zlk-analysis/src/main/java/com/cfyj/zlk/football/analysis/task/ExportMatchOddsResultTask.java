package com.cfyj.zlk.football.analysis.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.analysis.service.AnalysisServivce;
import com.cfyj.zlk.football.constant.FilepathConfig;
import com.cfyj.zlk.football.data.service.MatchService;
import com.cfyj.zlk.football.entity.Match;

import lombok.extern.slf4j.Slf4j;

/**
 * 导出当前期销售中的赔率文件
 * @author Exception
 *
 */
@Component
@Slf4j
public class ExportMatchOddsResultTask {

	@Autowired
	private AnalysisServivce analysisServivce;
	
	@Autowired
	private MatchService matchService;
	
	
	
	@Scheduled(fixedDelay=10000)
	public void task() {
//		List<Match> list = matchService.getCurrentSaleMatch2();
//		if(list!=null && list.size()>0) {
//			for(Match match: list) {			
//				try {
//					analysisServivce.exportMatchResult(match.getQtId(),FilepathConfig.EXPORT_CURRENTSALE_MATCHODDSRESULT_FILEPATH);
//				} catch (Exception e) {
//					log.error("导出当前期赔率数据失败",e);
//				}
//							
//			}
//		}
		
		List<Match> list = matchService.getMatchX();
		if(list!=null && list.size()>0) {
			for(Match match: list) {			
				try {
					analysisServivce.exportMatchResult(match.getQtId(),FilepathConfig.EXPORT_ANALYSIS_MATCHODDSRESULT_FILEPATH);
				} catch (Exception e) {
					log.error("导出当前期赔率数据失败",e);
				}
							
			}
		}
		
	}
	

	
}
