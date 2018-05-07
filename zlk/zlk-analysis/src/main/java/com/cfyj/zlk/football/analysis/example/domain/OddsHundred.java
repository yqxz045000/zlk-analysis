package com.cfyj.zlk.football.analysis.example.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OddsHundred {
	
	private Long match_qt_id ;
	private Integer provider_id;
	private String odds_new;
	private String  odds_old; 
	private String  odds_all; 
	private Date  create_time; 
	private Date  update_time; 
	
	
	
	
}
