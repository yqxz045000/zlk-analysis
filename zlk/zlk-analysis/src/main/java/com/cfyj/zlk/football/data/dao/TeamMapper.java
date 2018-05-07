package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cfyj.zlk.football.entity.Team;

@Mapper
public interface TeamMapper {

	List<Team> findByCnName(@Param("teamName")String teamName);
	
	@Select("select qt_id from fb_team t where FIND_IN_SET(#{teamName},t.alias)  limit 1;")
	Long matchingTeamByTeamName(@Param("teamName")String teamName);
	
	@Select("select qt_id from fb_team t where FIND_IN_SET(#{teamName},t.alias) limit 2;")
	List<Long> matchingTeamByTeamName2(@Param("teamName")String teamName);

	@Select("select qt_id from fb_team")
	List<Long> selectAllQtIds();
	
	Team selectByQtId(@Param("qt_id")Long teamId);
	
	void updateByIdSelective(Team team);
	
	void insertSelective(Team team);
}
