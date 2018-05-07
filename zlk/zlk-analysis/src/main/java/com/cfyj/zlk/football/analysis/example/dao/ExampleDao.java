package com.cfyj.zlk.football.analysis.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cfyj.zlk.football.analysis.example.domain.Match;
import com.cfyj.zlk.football.analysis.example.domain.OddsHundred;
import com.cfyj.zlk.football.entity.Odds;



@Mapper
public interface ExampleDao {

	@Select("select id,qt_id,home_team_id,home_team_name,guest_team_id,guest_team_name,full_score,half_score,match_type,match_time from fb_matches where  match_time > '2017-12-14 06:00:00' and match_time < '2018-04-14 22:00:00'   and full_score is not NULL and full_score != '' order by match_time desc ")
	List<Match> findCondition();
	
	@Select ("select odds_new from fb_odds_hundred where match_qt_id = #{qt_id} limit 1")
	OddsHundred findOddsOneByQtid(@Param("qt_id") Long qt_id);
	
	
	@Select ("select * from fb_odds_hundred order by create_time asc")
	List<OddsHundred> findAll();

	int insertbatch(List<Odds> list);
	
	@Select("select * from fb_ouyaodds2 where qtid = #{qt_id} and companyId = #{companyId}")
	Odds findOdds2OneByQtid(@Param("qt_id") Long qt_id ,@Param("companyId") String companyId );
	

}
