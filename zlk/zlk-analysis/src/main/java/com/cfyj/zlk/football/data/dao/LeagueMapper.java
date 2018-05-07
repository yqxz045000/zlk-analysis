package com.cfyj.zlk.football.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cfyj.zlk.football.entity.League;

@Mapper
public interface LeagueMapper {

	List<League> findByShortNameCn(String shortNameCn);

}
