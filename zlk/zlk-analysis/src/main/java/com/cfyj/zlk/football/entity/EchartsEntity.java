package com.cfyj.zlk.football.entity;

import java.util.List;

import lombok.Data;

@Data
public class EchartsEntity {

	private String name ;
	
	private String type ;
	
	private List<Double> data;



	public EchartsEntity() {
		super();
	}



	public EchartsEntity(String name, String type, List<Double> data) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
	}
	
	
	
}
