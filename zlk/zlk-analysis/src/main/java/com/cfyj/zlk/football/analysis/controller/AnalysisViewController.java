package com.cfyj.zlk.football.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cfyj.zlk.football.analysis.service.AnalysisServivce;

@RestController
@RequestMapping("/analysis")
public class AnalysisViewController {
	
	@Autowired
	private AnalysisServivce analysisServivce;
	
	
	//立博 82 ，威廉希尔 115，韦德 81，10BET 16，金宝博 499，bet365 281，betDAQ 54,澳门 80
	@PostMapping("/exportAnalysisView.do")
	public void getAnalysisView(long qtid) throws Exception {
			
		 analysisServivce.exportMatchResult(qtid);;
	}
	
	
	
}
