package com.cfyj.zlk.football.data.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
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

	List<OddsMatchVO> findMatchByTime(@Param("beginTime")  String beginTime, @Param("endTime")  String endTime);
	
	@Select("select * from fb_matches where  match_time between #{beginTime} and #{endTime} and full_score is not null and home_team_id>0 and guest_team_id>0  order by match_time asc ")
	@ResultMap("BaseResultMap")
	List<Match> findAnalysisMatchByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date ednTime);
	
	@Select("select m.* from fb_matches m where m.id in (select sm.match_id from fb_stage s LEFT JOIN fb_stage_matches sm on sm.stage_id = s.id  and sm.match_id is not NULL  where s.is_current=1 and s.type =4  ); ")
	@ResultMap("BaseResultMap")
	List<Match> getCurrentSaleMatch2();
}