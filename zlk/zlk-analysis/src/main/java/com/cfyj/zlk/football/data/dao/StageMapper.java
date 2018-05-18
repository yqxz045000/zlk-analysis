package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cfyj.zlk.football.entity.Stage;

@Mapper
public interface StageMapper {
	

	Stage selectByNameAndType(@Param("type")int ordinal,@Param("name") String name);

	
//	@Select("select sm.* from fb_stage_matches sm where sm.stage_id in (select t.* from (select s.id from fb_stage s  where s.type = #{type} and s.is_current = 0 order by s.id desc  limit 2) t) order by sm.match_time desc")
//	List<StageMatch> findHistory2(@Param("type") int type);
	
	/***************************************edit*****************************************************8/

	/**
	 * 所有当前期的全部设置为非当前期
	 * @param ordinal
	 * @return
	 */
	@Update("update fb_stage s set s.is_current=false,s.update_time=now()  where s.type=#{type} and s.is_current=true")
	int updateByCurrentFalse(@Param("type") int ordinal);
	
	void updateDefineCurrentTrue(@Param("jcType")int jcType,@Param("compareTime") String compareTime);
	
	void insert(Stage oldstage);
	
	void updateByPrimaryKey(Stage oldstage);

	/**
	 * 查询期次列表
	 * @param stageType
	 * @param isCurrent
	 * @return
	 */
	List<Stage> getStageByType(@Param("stageType")int stageType, @Param("isCurrent")boolean isCurrent);

	/**
	 * 查询期次列表(带条数限制)
	 * @param stageType
	 * @param isCurrent
	 * @return
	 */
	List<Stage> getStageByTypeLimit(@Param("stageType")int stageType, @Param("isCurrent")boolean isCurrent, @Param("limit")int limit);

	@Select("select is_current from fb_stage where id = #{stageId}")
	int isCurrent(@Param("stageId") Long stageId);

	@Select ("select name from fb_stage  where id = #{stageId}")
	String findNameById(Long stageId);

	@Select("select name from fb_stage where  is_current = 1 and type = #{type} limit 1")
	List<Stage> findCurrentName(@Param("type") int type);
}
