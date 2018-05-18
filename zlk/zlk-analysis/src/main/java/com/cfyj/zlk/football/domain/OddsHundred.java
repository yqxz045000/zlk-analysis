package com.cfyj.zlk.football.domain;

import java.io.Serializable;
import java.util.Date;

public class OddsHundred implements Serializable {
	private Long matchQtId;

	private Integer providerId;

	private String oddsNew;

	private String oddsOld;

	private Date createTime;

	private Date updateTime;

	private String oddsAll;

	private static final long serialVersionUID = 1L;

	public Long getMatchQtId() {
		return matchQtId;
	}

	public void setMatchQtId(Long matchQtId) {
		this.matchQtId = matchQtId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getOddsNew() {
		return oddsNew;
	}

	public void setOddsNew(String oddsNew) {
		this.oddsNew = oddsNew == null ? null : oddsNew.trim();
	}

	public String getOddsOld() {
		return oddsOld;
	}

	public void setOddsOld(String oddsOld) {
		this.oddsOld = oddsOld == null ? null : oddsOld.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOddsAll() {
		return oddsAll;
	}

	public void setOddsAll(String oddsAll) {
		this.oddsAll = oddsAll == null ? null : oddsAll.trim();
	}
}