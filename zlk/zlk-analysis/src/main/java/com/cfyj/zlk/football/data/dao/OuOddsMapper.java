package com.cfyj.zlk.football.data.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cfyj.zlk.football.entity.Odds;

@Mapper
public interface OuOddsMapper {
	
	@Select("select * from fb_ouyaodds2 where qtid = #{qtid} and companyId = #{companyId}")
	Odds findByQtidAndCompanyId(@Param("qtid")Long qtid, @Param("companyId") String companyId);
	
//	@Insert("insert into fb_ouyaodds2(qtid,companyId,oldOdds,newOdds,allOdds,type,createTime,updateTime) values (#{qtid},#{companyId},#{oldOdds},#{newOdds},#{allOdds},#{type},#{createTime},#{updateTime})  ")
	int insert(@Param("qtid") long qtid,@Param("companyId")String companyId, @Param("oldOdds")String oldOdds,@Param("newOdds") String newOdds, @Param("allOdds")String allOdds, @Param("type")int type, 
			@Param("createTime") Timestamp createTime ,@Param("updateTime")  Timestamp updateTime);

}
