package com.cfyj.zlk.football.analysis.example.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MatchResult {
	
	private Long qtid ;
	private String homeName;
	private String guestName;
	private String fullSocre;
	private String halfScore;
	private String cg;
	private String cgOdds;
	
	private String spf;
	private String successRate;
	private Date matchTime;
	
	
	

}
