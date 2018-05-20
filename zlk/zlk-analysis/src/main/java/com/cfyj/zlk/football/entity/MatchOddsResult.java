package com.cfyj.zlk.football.entity;

import java.util.Date;

import lombok.Data;

@Data
public class MatchOddsResult {
	
	private Long id ;
	private Long qtid;
	private String companyId ; 
	private int oddsUpdateNum;
	
	private Integer fhl; //返还率
	private Double nwinOdds;
	private Double ndrawOdds;
	private Double nfailOdds;
	private Double owinOdds;
	private Double odrawOdds;
	private Double ofailOdds;
	
	private Double nwinOddsRate;
	private Double ndrawOddsRate;
	private Double nfailOddsRate;
	private Double owinOddsRate;
	private Double odrawOddsRate;
	private Double ofailOddsRate;
	
	
	
	private Double nwinOddsKl;
	private Double ndrawOddsKl;
	private Double nfailOddsKl;
	private Double owinOddsKl;
	private Double odrawOddsKl;
	private Double ofailOddsKl;
	
	private String allOdds;
	
	private Integer type ;//类型
	
	/********比赛相关的*************/
	private Integer matchType;
	
	private String fullScore ;  //A-A
	
	private String halfScore ; //A-A
	
	private Integer matchResult ;//  0 未完成  ，1主胜， 2平， 3 客胜
	
	private String hn ;
	
	private String gn ; 
	
	private Long hid ;
	
	private Long gid ; 
	
	private Long leagueId;
	
	private Date matchTime ; 
	
	private String hr;
	private String gr ; 
	
	//立博 ，威廉希尔，韦德，10ET，金宝博，bet365，betDAQ,澳门
	
	
	
}
