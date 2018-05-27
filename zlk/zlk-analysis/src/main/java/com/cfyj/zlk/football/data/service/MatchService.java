package com.cfyj.zlk.football.data.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.MatchMapper;
import com.cfyj.zlk.football.domain.OddsMatchVO;
import com.cfyj.zlk.football.entity.Match;

@Service
public class MatchService {

	private Logger log = LoggerFactory.getLogger(MatchService.class);

	@Autowired
	private MatchMapper matchMapper;


	public Match getMatchByQtId(Long qtId) {
		return matchMapper.findByQtid(qtId);
	}

	public void save(Match match) {
		matchMapper.insert(match);
	}
	
	public void update(Match match) {
		matchMapper.update(match);
	}

	public int updateScoreAndstate(Match match) {
		return matchMapper.updateScoreAndstate(match);
	}

	public int count() {
		return matchMapper.count();
	}

	public List<Match> getByLimit(int begin, int end) {
		return matchMapper.findByLimit(begin, end);
		
	}

	public List<OddsMatchVO> getCurrentSaleMatch() {
		// TODO Auto-generated method stub
		return matchMapper.findCurrentSaleMatch();
	}

	public List<Match> getAnalysisMatchByLimit(int begin, int end) {
		
		return matchMapper.getAnalysisMatchByLimit(begin,  end);
	}

	public int countByAnalysisMatch() {

		return matchMapper.countByAnalysisMatch();
	}

	public List<OddsMatchVO> getMatchByTime(String beginTime, String endTime) {
		return matchMapper.findMatchByTime( beginTime,  endTime) ; 
	}

	public List<Match> getAnalysisMatchByTime(Date beginTime, Date ednTime) {
		// TODO Auto-generated method stub
		return matchMapper.findAnalysisMatchByTime(beginTime, ednTime);
	}
	
	public List<Match> getCurrentSaleMatch2() {
		// TODO Auto-generated method stub
		return matchMapper.getCurrentSaleMatch2();
	}
	
	
}