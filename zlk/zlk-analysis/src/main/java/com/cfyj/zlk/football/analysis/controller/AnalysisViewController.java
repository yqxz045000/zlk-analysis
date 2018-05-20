package com.cfyj.zlk.football.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.cfyj.zlk.football.analysis.service.AnalysisServivce;

public class AnalysisViewController {
	
	@Autowired
	private AnalysisServivce analysisServivce;
	
	
	
	@PostMapping("getAnalysisView")
	public Object getAnalysisView() {
		
		
		return null;
	}
	
	
	
}
