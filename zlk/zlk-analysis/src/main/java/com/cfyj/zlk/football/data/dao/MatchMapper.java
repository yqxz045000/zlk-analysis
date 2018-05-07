package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cfyj.zlk.football.entity.Match;

@Mapper
public interface MatchMapper {

	Match findByQtid(@Param("qtId") Long qtId);

	int insert(Match match);

	int update(Match match);
	
	int updateScoreAndstate(Match match);

	int count();

	List<Match> findByLimit(@Param("begin")int begin, @Param("end") int end);
}