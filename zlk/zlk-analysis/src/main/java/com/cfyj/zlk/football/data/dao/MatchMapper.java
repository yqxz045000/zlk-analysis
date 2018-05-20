package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cfyj.zlk.football.domain.OddsMatchVO;
import com.cfyj.zlk.football.entity.Match;

@Mapper
public interface MatchMapper {

	Match findByQtid(@Param("qtId") Long qtId);

	int insert(Match match);

	int update(Match match);
	
	int updateScoreAndstate(Match match);

	int count();

	List<Match> findByLimit(@Param("begin")int begin, @Param("end") int end);
	
	
	@Select("select qt_id from fb_matches where id = #{id}")
	Long findQtidById(@Param("id")Long id);

	List<OddsMatchVO> findCurrentSaleMatch();

	List<Match> getAnalysisMatchByLimit(@Param("begin")int begin, @Param("end") int end);
	
	@Select("select count(id) from fb_matches where full_score is not null and home_team_id>0 and guest_team_id>0 and match_time is not null")
	int countByAnalysisMatch();
}