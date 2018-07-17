package com.cfyj.zlk.football.analysis.service;

public interface AnalysisServivce {
	
	/**
	 * 统计当前期比赛赔率的结果
	 */
	void arrangementAnalysisOddsResult();
	
	
	void exportMatchResult(long qtid,String path) throws  Exception ;


	void exportMatchResultToJson(Long qtId, String path)throws Exception;

}
