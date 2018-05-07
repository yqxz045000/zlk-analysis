package com.cfyj.zlk.football.entity;

import java.io.Serializable;
import java.util.Date;

public class Team implements Serializable {
    private Long qtId;

    private String nameCn;

    private String shortNameCn;

    private String nameEn;

    private String alias;
 
    private String pic;

    private String city;

    private String stadium;//球场

    private String capacity;//容量

    private String buildDate;//建造日期

    private String website;//主站

    private String address;//联系地址

    private Boolean isCountry;//是否国家队

    private String coach;//教练

    private Date lastUpdateTime;

    private Boolean isUpdate;

    private Date createTime;

    private Date updateTime;

    private String resume;//球队简介

    private String rearguard;//后卫

    private String vanguard;//前锋

    private String goalkeeper;//守门员

    private String midfielder;//中场
    
    private String lineupDetail;//球队阵容详细信息
    
    private String teamCharacter;//球队特点
    
    private String teamHonor;// 球队荣誉

    private static final long serialVersionUID = 1L;

    public Long getQtId() {
        return qtId;
    }

    public void setQtId(Long qtId) {
        this.qtId = qtId;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getShortNameCn() {
        return shortNameCn;
    }

    public void setShortNameCn(String shortNameCn) {
        this.shortNameCn = shortNameCn == null ? null : shortNameCn.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium == null ? null : stadium.trim();
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity == null ? null : capacity.trim();
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate == null ? null : buildDate.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Boolean getIsCountry() {
        return isCountry;
    }

    public void setIsCountry(Boolean isCountry) {
        this.isCountry = isCountry;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach == null ? null : coach.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume == null ? null : resume.trim();
    }

    public String getRearguard() {
        return rearguard;
    }

    public void setRearguard(String rearguard) {
        this.rearguard = rearguard == null ? null : rearguard.trim();
    }

    public String getVanguard() {
        return vanguard;
    }

    public void setVanguard(String vanguard) {
        this.vanguard = vanguard == null ? null : vanguard.trim();
    }

    public String getGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(String goalkeeper) {
        this.goalkeeper = goalkeeper == null ? null : goalkeeper.trim();
    }

    public String getMidfielder() {
        return midfielder;
    }

    public void setMidfielder(String midfielder) {
        this.midfielder = midfielder == null ? null : midfielder.trim();
    }

	public String getLineupDetail() {
		return lineupDetail;
	}

	public void setLineupDetail(String lineupDetail) {
		this.lineupDetail = lineupDetail;
	}

	public String getTeamCharacter() {
		return teamCharacter;
	}

	public void setTeamCharacter(String teamCharacter) {
		this.teamCharacter = teamCharacter == null ? null : teamCharacter.trim();
	}

	public String getTeamHonor() {
		return teamHonor;
	}

	public void setTeamHonor(String teamHonor) {
		this.teamHonor = teamHonor;
	}
}