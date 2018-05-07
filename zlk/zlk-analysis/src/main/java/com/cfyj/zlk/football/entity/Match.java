package com.cfyj.zlk.football.entity;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class Match {

	private Long id;

	private Long qtId;

	private Long seasonId;

	private Long leagueId;

	private String leagueName;

	private Long hid;

	private String hn;

	private Long gid;

	private String gn;

	private String fullScore;

	private String halfScore;

	private Integer matchType;// -1：完 ，0：未开赛，1:上半场，2：中场，3：下半场， -12:腰斩，-10：取消，-11：待,4:加时

	private String homeRank;

	private String guestRank;

	private String groupName;

	private Date matchTime;

	private Timestamp createTime;

	private Timestamp updateTime;

}