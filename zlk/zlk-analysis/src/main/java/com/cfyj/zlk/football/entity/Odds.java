package com.cfyj.zlk.football.entity;

import java.sql.Timestamp;

import lombok.Data;


public class Odds {
	 //281-韦德  82-立博  115-威廉希尔
	private Long id;

	private Long qtid;
	
	private String companyId;
	
	private String newOdds;//主 平 客 返还率
	
	private String oldOdds;
	
	private String allOdds;//只有非初的所有即时赔率，所以在获取所有赔率，需要拿到all和oldp去组装  格式为 newO1,time1(MM-dd HH);newO2,time2(MM-dd HH)
	
	private int type;//OddsType ou 1 ,ya 2, dx 3 ,bf 4, kl 5
	
	private Timestamp createTime;
	
	private Timestamp updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQtid() {
		return qtid;
	}

	public void setQtid(Long qtid) {
		this.qtid = qtid;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getNewOdds() {
		return newOdds;
	}

	public void setNewOdds(String newOdds) {
		this.newOdds = newOdds;
	}

	public String getOldOdds() {
		return oldOdds;
	}

	public void setOldOdds(String oldOdds) {
		this.oldOdds = oldOdds;
	}

	public String getAllOdds() {
		return allOdds;
	}

	public void setAllOdds(String allOdds) {
		this.allOdds = allOdds;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Odds [id=" + id + ", qtid=" + qtid + ", companyId=" + companyId + ", newOdds=" + newOdds + ", oldOdds="
				+ oldOdds + ", allOdds=" + allOdds + ", type=" + type + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}


	
		
}
