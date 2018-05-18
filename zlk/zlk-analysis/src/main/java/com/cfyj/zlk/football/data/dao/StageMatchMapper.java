package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cfyj.zlk.football.entity.StageMatch;

@Mapper
public interface StageMatchMapper {

	StageMatch findBySortAndStageId(@Param("sort") Integer sort,@Param("stageId") Long stageId);

	void updateByPrimaryKey(StageMatch oldStageMatch);


	List<StageMatch> findHistory(@Param("type")int type,@Param("qc") int qc);

	StageMatch findByQtid(Long qtId);
	
	StageMatch findByMatchId(Long matchId);
	
	List<StageMatch> findMatchingFailMatch();

	int updateMatchId(StageMatch sm);

	 int updateStageMatchByInner(StageMatch stageMatch);
	 
	 void insert(StageMatch sm);
	 /**
	  * 取出stageMatch和关联的qtid
	  * @param sort
	  * @param stageId
	  * @return
	  */
	StageMatch findStageMatchForMatching(@Param("sort") Integer sort,@Param("stageId")  Long stageId);

	
	/******************主站数据和球探数据匹配sql********************************/
	List<Long> matchingStageMatch(@Param("homeName")String homeName,@Param("guestName") String guestName,@Param("matchTime") String matchTime);
	
//	@Select("select m.id from fb_matches m where m.match_time = #{matchtime} and m.home_team_name = #{hn} and m.guest_team_name  = #{gn} order by m.qt_id limit 1")
//	List<Long> matchingStageMatch2(@Param("hn")String hn, @Param("gn")String gn,@Param("matchtime") String matchtime);
	
	List<Long> matchingStageMatch3(@Param("hTeamId")Long hTeamId,@Param("gTeamId") Long gTeamId,@Param("matchTime") String matchTime);
	
//	@Select("select m.id from fb_matches m where m.league_name = #{mname} and m.match_time = #{matchTime}")
//	List<Long> matchingStageMatch4(@Param("mname")String mname, @Param("matchTime")String matchTime);
	
	
	List<Long> matchingStageMatch5(@Param("homeName") String hn,@Param("guestName")  String gn,@Param("beginTime")  String beginTime,@Param("endTime")  String endTime);

	List<Long> matchingStageMatch6(@Param("hTeamId")  Long hTeamId,@Param("gTeamId")  Long gTeamId,@Param("beginTime")  String beginTime,@Param("endTime")  String endTime);


}
