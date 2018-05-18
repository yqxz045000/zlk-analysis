package com.cfyj.zlk.football.entity;

import java.util.Date;

/**
 * 主站数据接收实体
 * 
 * @author ls
 * @2018年1月15日
 */
public class StageMatch  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7432973927162129738L;

	private Long id;

	private Boolean available;

	private String mname;

	private Date createTime;

	private Boolean status;

	private Date updateTime;

	private String gn;

	private String hn;

	private Long itemId;

	private String jcName;

	private Date matchTime;

	private Integer rq;

	private Integer sort;

	private Long matchId;

	private Long stageId;

	private Boolean swapTeam = false;

	private Integer mid;

	private String grade;

	private String rqspf;

	private String spf;

	// private String cbf;
	//
	// private String bqc;
	//
	// private String jqs;

	private Integer updateStatus;

	private String iaudit;// 主站返回的比赛是否结束的标识，只有0和1，为1则标识结束， 0标识未结束

	private String fullScore;// 全场比分

	private Long qtid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname == null ? null : mname.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getGn() {
		return gn;
	}

	public void setGn(String gn) {
		this.gn = gn == null ? null : gn.trim();
	}

	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn == null ? null : hn.trim();
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getJcName() {
		return jcName;
	}

	public void setJcName(String jcName) {
		this.jcName = jcName == null ? null : jcName.trim();
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public Integer getRq() {
		return rq;
	}

	public void setRq(Integer rq) {
		this.rq = rq;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Boolean getSwapTeam() {
		return swapTeam;
	}

	public void setSwapTeam(Boolean swapTeam) {
		this.swapTeam = swapTeam;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade == null ? null : grade.trim();
	}

	public String getRqspf() {
		return rqspf;
	}

	public void setRqspf(String rqspf) {
		this.rqspf = rqspf == null ? null : rqspf.trim();
	}

	public String getSpf() {
		return spf;
	}

	public void setSpf(String spf) {
		this.spf = spf == null ? null : spf.trim();
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getIaudit() {
		return iaudit;
	}

	public void setIaudit(String iaudit) {
		this.iaudit = iaudit;
	}

	public String getFullScore() {
		return fullScore;
	}

	public void setFullScore(String fullScore) {
		this.fullScore = fullScore;
	}

	public Long getQtid() {
		return qtid;
	}

	public void setQtid(Long qtid) {
		this.qtid = qtid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gn == null) ? 0 : gn.hashCode());
		result = prime * result + ((hn == null) ? 0 : hn.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((stageId == null) ? 0 : stageId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StageMatch other = (StageMatch) obj;
		if (gn == null) {
			if (other.gn != null)
				return false;
		} else if (!gn.equals(other.gn))
			return false;
		if (hn == null) {
			if (other.hn != null)
				return false;
		} else if (!hn.equals(other.hn))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (stageId == null) {
			if (other.stageId != null)
				return false;
		} else if (!stageId.equals(other.stageId))
			return false;
		return true;
	}

}