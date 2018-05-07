package com.cfyj.zlk.football.entity;

import java.io.Serializable;
import java.util.Date;

public class League implements Serializable {
    private Long id; //对应球探的id

    private String shortNameCn;

    private String fullNameCn;

    private String shortNameEn;

    private String fullNameEn;

    private String color;

    private String pic;

    private String curseason;

    private Integer model; //页面表现形式  type 为联赛的时候有用：  0：普通联赛 ，1：为杯赛样子的联赛 
    
    private Integer type;// 1：联赛 ，2：杯赛

    private Long countryId;

    private Boolean state;

    private Boolean available;

    private Date createTime;

    private Date updateTime;

    private Boolean hot;//热门赛事

    private int orderNum;//控制联赛的显示顺序
    
    private static final long serialVersionUID = 1L;

    public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortNameCn() {
        return shortNameCn;
    }

    public void setShortNameCn(String shortNameCn) {
        this.shortNameCn = shortNameCn == null ? null : shortNameCn.trim();
    }

    public String getFullNameCn() {
        return fullNameCn;
    }

    public void setFullNameCn(String fullNameCn) {
        this.fullNameCn = fullNameCn == null ? null : fullNameCn.trim();
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn == null ? null : shortNameEn.trim();
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn == null ? null : fullNameEn.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getCurseason() {
        return curseason;
    }

    public void setCurseason(String curseason) {
        this.curseason = curseason == null ? null : curseason.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }
    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

}