package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cfyj.zlk.football.entity.MatchOddsResult;

@Mapper
public interface MatchOddsResultMapper {

	int batchInsert(@Param("results") List<MatchOddsResult> results);
	
	@Select("select * from al_matchoddsresult t where t.qtid = #{qtid}")
	List<MatchOddsResult> findByQtid(@Param("qtid") long qtid);
	
	@Select("select count(qtid) from al_matchoddsresult where qtid = #{qtid} and companyId = #{companyId}")
	int isExitByQqtidAndCompanyId(@Param("qtid") long qtid, @Param("companyId") String companyId);

	void insert(MatchOddsResult mor);

	void update(MatchOddsResult mor);

}
