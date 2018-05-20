package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cfyj.zlk.football.entity.MatchOddsResult;

@Mapper
public interface MatchOddsResultMapper {

	int batchInsert(@Param("results") List<MatchOddsResult> results);

}
