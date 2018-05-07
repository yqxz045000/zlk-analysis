package com.cfyj.zlk.football.constant;

public class URLConstant {
	
	
	/***************************inner url***********************************/
	
	private static final String REQUEST_INNER_DOMAIN = "http://iphone.weicaiwan.com" ;
	
	private static final String REQUEST_BJTC_DOMAIN = "http://www.bjlot.com" ;
	
	public final static String INNER_ZQ_JC_URL = REQUEST_INNER_DOMAIN + "/data/jincai/jc_hh_zlkgames.xml";//竞彩足球
	
	public final static String INNER_ZQ_BD_URL = REQUEST_INNER_DOMAIN + "/data/bd/";//北单足球  http://www.9188.com/data/bd/180105/spf.xml 
	
	public final static String BJTC_ZQ_BD_URL = REQUEST_BJTC_DOMAIN + "/data/200ParlayGetGame.xml";//北京北单足球网北单足球
	
	public final static String IAUDIT_TRUE = "1"; //主站返回的标识：如果为1则代表比赛已经结束
	
	public final static String INNER_ZQ_ODDS_URL = REQUEST_INNER_DOMAIN+"/data/jincai/sp/"; //例 ：http://www.9188.com/data/jincai/sp/180124/180124028.xml
	
	
	/******************************qt url*****************************************/
	
	public static final String REQUEST_QT_DOMAIN = "http://interface.win007.com";
	
	public static final String REQUEST_QTPAGE_DOMAIN = "http://zq.win007.com";

	public final static String QT_FUTUREMATCH_URL = "http://bf.win007.com/football/Next_"; //未来赛程 http://bf.win007.com/football/Next_20180429.htm
	
	public final static String QT_FINISHMATCH_URL = "http://bf.win007.com/football/Over_"; //完赛赛程  http://bf.win007.com/football/hg/Over_20140928.htm
	
	public final static String FOOTBALL_STARTLINEUP_URL = REQUEST_QT_DOMAIN + "/zq/lineup.aspx?cmd=new";// 出场阵容新版接口

	public final static String FOOTBALL_MATCH_TODAYINFO_URL = REQUEST_QT_DOMAIN +"/zq/today.aspx";// 当天比赛信息 //<qtid,MatchCommand>

	public final static String FOOTBALL_MATCH_JSINFO_URL = REQUEST_QT_DOMAIN +"/zq/change.xml"; // 即时比赛信息//<qtid,MatchJsInfo>

	public final static String FOOTBALL_MATCH_JIFEN_URL = REQUEST_QT_DOMAIN +"/zq/jifen.aspx"; // 积分接口地址

	public final static String FOOTBALL_MATCH_FORECAST_URL = REQUEST_QT_DOMAIN +"/zq/Injury_new.aspx"; //伤停预测、赛前简报地址

	public final static String FOOTBALL_MATCH_TEAMSUMMARY_URL = REQUEST_QTPAGE_DOMAIN+"/cn/team/Summary/";  	//球队技术面比赛特征的接口前缀
	
	public final static String FOOTBALL_PLAYER_URL =  REQUEST_QT_DOMAIN +"/zq/Player_XML.aspx";		//球员资料
	
//	public final static String QT_WEBSITE_PRE = "http://zq.win007.com";									//球探网前缀
	
	public final static String QT_LEAGUE_DATA_PRE = REQUEST_QTPAGE_DOMAIN+"/cn/League/";			//联赛前缀
	
	public final static String QT_SUBLEAGUE_DATA_PRE = REQUEST_QTPAGE_DOMAIN+"/cn/SubLeague/";		//子联赛前缀
	
	public final static String QT_MATCH_LIVE_URL = REQUEST_QT_DOMAIN +"/zq/detail.aspx?type=new"; //三、当天比赛的入球、红黄牌事件  当天比赛实况和技术统计(废弃，不能获取实况的信息) 
	
	public final static String QT_MATCHLIVER_EVENT_URL = REQUEST_QT_DOMAIN +"/zq/Event_XML.aspx?type=new"; //比赛详细事件接口（历史数据）  填充实况 比赛时间
	
	public final static String QT_MATCHLIVER_JSTJ_URL = REQUEST_QT_DOMAIN +"/zq/Technic_XML.aspx"; //一天内比赛的技术统计 填充实况 技术统计
	

	public final static String QT_MATCH_FORECAST_URL_PRE = REQUEST_QTPAGE_DOMAIN+"/analysis/";				//伤停预测球探接口前缀

	public final static String QT_MATCH_MODIFYTYPE_URL = REQUEST_QT_DOMAIN +"/zq/ModifyRecord.aspx"; 	//十八、比赛删除，改时间记录（限制90秒/次）
	
	public final static String QT_FUTUREMATCHSEC_URL = REQUEST_QTPAGE_DOMAIN+"/cn/team/TeamScheAjax.aspx"; //未来赛程Sec

	/***********************************odds********************************************/
	public final static String QT_ODDS_OUYA_URL = REQUEST_QT_DOMAIN + "/zq/odds.aspx"; //十一、 赔率部分：即时赔率接口(限制15秒/次) 
	
	
	/******************************team logo url*****************************/
	
	public static String FOOTBALL_TEAMLOGO_URL = "http://zlk.weicaiwan.com/img/football/team/";
	
	
	
	/**********分析版本新加的爬取url**********/
	public final static String QT_HIS_OU_ODDS_URL = "http://1x2.nowscore.com/";  //http://1x2.nowscore.com/1444761.js
	
}
