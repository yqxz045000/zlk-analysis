package com.cfyj.zlk.football.analysis.example.domain;

import java.util.Date;

import com.cfyj.zlk.football.entity.Odds;

import lombok.Data;

@Data
public class Match {
	
	private Long qt_id ; 
	private Long home_team_id; 
	private String home_team_name; 
	private Long guest_team_id; 
	private String guest_team_name; 
	private String full_score; 
	private String half_score; 
	private Integer  match_type; 
	private String home_rank; 
	private String guest_rank; 
	private Date match_time; 
//	private List<OddsHundred> oddss;
	
	private Odds odds;

	/********************************分析结果***********************************************************************************/	
	private String winHome;	
	private String winOdds;	
	private Double winProbability; //赢的概率	
	private String spf;	
	private String spfProbability; //胜平负的概率	
	private Integer oddsUpdateNum; //赔率更新次数
	private Integer bigRate = 0; //是否为大概率
	private Double fhl ; //返还率
	
	
}
