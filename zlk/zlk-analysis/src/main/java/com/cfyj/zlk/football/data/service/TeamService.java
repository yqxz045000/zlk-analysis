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
	
	/**
	 * 通过球队名称匹配球队别名，如果只有一条记录则返回球队id，如果有多条则返回null
	 * @param teamName
	 * @return
	 */
	public Long matchingTeamByTeamName2(String teamName) {
		List<Long> ids = teamMapper.matchingTeamByTeamName2(teamName);
		if(ids!=null && ids.size()==1) {
			return ids.get(0);
		}
		return null;
	}

}
