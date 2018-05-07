package com.cfyj.zlk.football.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.LeagueMapper;
import com.cfyj.zlk.football.entity.League;

@Service
public class LeagueService {

	@Autowired
	private LeagueMapper leagueMapper;
	
	public List<League> findByShortNameCn(String leagueName) {
		return leagueMapper.findByShortNameCn(leagueName);
	}

}
