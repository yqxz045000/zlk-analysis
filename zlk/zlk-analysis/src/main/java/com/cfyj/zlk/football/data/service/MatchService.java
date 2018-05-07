package com.cfyj.zlk.football.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.MatchMapper;
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
	
	
	
}