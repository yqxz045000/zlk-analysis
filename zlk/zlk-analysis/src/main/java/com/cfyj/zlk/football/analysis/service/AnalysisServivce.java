package com.cfyj.zlk.football.analysis.service;

import java.util.Map;

public interface AnalysisServivce {
	
	/**
	 * 统计当前期比赛赔率的结果
	 */
	void arrangementAnalysisOddsResult();
	
	
	void exportMatchResult(long qtid,String path) throws  Exception ;


	Map<String, Object> getLineCart(long qtid,String companyId);

	void exportMatchResultToJson(Long qtId, String path)throws Exception;


}
