package com.cfyj.zlk.football.entity;

import java.io.Serializable;
import java.util.Date;

public class Stage implements Serializable {
    private Long id;

    private String name;

    private Integer type;

    private Boolean isCurrent;

    private Boolean available;

    private Boolean status;

    private Date createTime;

    private Date updateTime;

    //下面变量位置不要改变！！
	public enum StageType {
		SF("足彩") ,//0
		JQ("进球彩"),//1
		BQ("半全场"),//2
		BD("北单"),//3
		JC("竞彩");//4
		private final String value;
		private StageType(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
    
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
}