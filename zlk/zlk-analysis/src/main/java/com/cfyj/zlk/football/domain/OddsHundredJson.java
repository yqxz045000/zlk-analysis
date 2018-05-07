package com.cfyj.zlk.football.domain;

import java.io.Serializable;

public class OddsHundredJson implements Serializable{
	private static final long serialVersionUID = -3340728662176033252L;
	private double p1;//主胜赔率
	private double p2;//平局赔率
	private double p3;//客胜赔率
	private String time;//日期
	public double getP1() {
		return p1;
	}
	public void setP1(double p1) {
		this.p1 = p1;
	}
	public double getP2() {
		return p2;
	}
	public void setP2(double p2) {
		this.p2 = p2;
	}
	public double getP3() {
		return p3;
	}
	public void setP3(double p3) {
		this.p3 = p3;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}	
