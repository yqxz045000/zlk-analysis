package com.cfyj.zlk.football.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.TeamMapper;
import com.cfyj.zlk.football.entity.Team;

@Service
public class TeamService {
	
	private Logger log = LoggerFactory.getLogger(TeamService.class);
	
	@Autowired
	private TeamMapper teamMapper;

	public List<Team> findByCnName(String teamName) {
		return teamMapper.findByCnName(teamName);
	}

}
