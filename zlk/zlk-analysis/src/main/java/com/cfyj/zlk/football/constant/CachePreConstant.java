package com.cfyj.zlk.football.constant;

/**
 * 缓存前缀常量
 * @author ls
 * @2017年12月26日
 */
public class CachePreConstant {
	
	/********************************比赛列表********************************************************************/
	public static final String  ZQ_MATCHING_QTIDS = "ZQ_MATCHING_QTIDS";//正在比赛的比赛    qtid,matchID;qtid,matchId;..		2
	
	public static final String  ZQ_UNFINISH_MATCH_LIST = "ZQ_UNFINISH_MATCH_LIST";//未完赛列表  Set<MatchInfoCache>		5
		
	public static final String  ZQ_JC_UNFINISH_MATCH_LIST = "ZQ_JC_UNFINISH_MATCH_LIST";//竞彩 未完赛列表	List<MatchCommand> 	5
	
	public static final String  ZQ_JC_FINISH_MATCH_LIST = "ZQ_JC_FINISH_MATCH_LIST";//竞彩 已完赛列表	List<MatchCommand>		5
	
	
	public static final String  ZQ_BD_UNFINISH_MATCH_LIST = "ZQ_BD_UNFINISH_MATCH_LIST";//北单未完赛列表	List<MatchCommand> unfinishList	5
	
	public static final String  ZQ_BD_FINISH_MATCH_LIST = "ZQ_BD_FINISH_MATCH_LIST";//北单已完赛列表	List<MatchCommand> finishList	5
	
	/*******************************QT 数据缓存*****************************************************/
	
	public static final String ZQ_LINEUP = "ZQ_LINEUP"; //ZQ_LINEUP Map<String,LineUp>  <MatchQtId,LineUp>			2
	
	public static final String  ZQ_QT_MATCHTODYINFO_PRE = "ZQ_QT_MATCHTODYINFO_"; //当天比赛的信息 #{qtid}  MatchCommand		3
	
	public static final String ZQ_MATCHLIVE = "ZQ_MATCHLIVE";//比赛实况   Map<String,MatchLive>   <qtid,MatchLive>  废弃		2

	public static final String ZQ_MATCH_JSINFO = "ZQ_MATCH_JSINFO"; //比赛即时比分		Map<String, MatchJsInfo>	<qtid,MatchJsInfo>		2
	
	public static final String ZQ_MODIFY_MATCHTYPE = "ZQ_MODIFY_MATCHTYPE"; //更改比赛状态的缓存map Map<String,ModifyMatchType> <qtid,ModifyMatchType>	2
	
	public static final String ZQ_MATCHLIVE_EVENT = "ZQ_MATCHLIVE_EVENT"; //比赛实况：事件
	
	public static final String ZQ_MATCHLIVE_JSTJ = "ZQ_MATCHLIVE_JSTJ"; //比赛实况：技术统计			
	
	/***********************************中间数据缓存**********************************************************/
	
	public static final String  ZQ_JC_INNERMATCH_LIST = "ZQ_JC_INNERMATCH_LIST"; //主站返回的比赛数据 	List<StageMatch>   2
	
	public static final String  ZQ_JC_HISTORY2MATCH_LIST = "ZQ_JC_HISTORY2MATCH_LIST"; //历史两期的比赛数据  	List<StageMatch>	2
	
	public static final String  ZQ_BD_INNERMATCH_LIST = "ZQ_BD_INNERMATCH_LIST"; //体彩当前期返回的未结束比赛数据 	List<StageMatch> 	2
		
	public static final String  ZQ_BD_HISTORY2MATCH_LIST = "ZQ_BD_HISTORY2MATCH_LIST"; //当前期结束的比赛和历史2期比赛	List<StageMatch>	2
	
	public static final String ZQ_BD_INNERODDS_STAGE_LIST = "ZQ_BD_INNERODDS_STAGE_LIST"; //北单当前期次和历史2期期次	List<Stage>	2
	
	public static final String ZQ_JC_INNERODDS_PRE = "ZQ_JC_INNERODDS_"; //竞彩主站赔率	 Map<String, String>		14
	
	public static final String ZQ_BD_INNERODDS_PRE = "ZQ_BD_INNERODDS_"; //北单主站赔率	 Map<String, String>		14
	
	
	public static final String ZQ_MATCH_FORECAST = "ZQ_MATCH_FORECAST";	//24小时内的伤停缓存 Map<String,MatchForecast> 这是生成阵容面的依据<matchQtId,MatchForecast>		7

	public static final String ZQ_LEAGUECUP_RANK = "ZQ_LEAGUECUP_RANK";	//积分排名缓存 Map<String,LeagueCupTeamScore>  <qtTeamId,LeagueCupTeamScore>				7
	
}
//	public static final String  ZQ_BD_INNERMATCH_WC_LIST = "ZQ_BD_INNERMATCH_WC_LIST"; //体彩当前期返回的结束比赛数据 	List<StageMatch>  
//	public static final String ZQ_JC_INNERODDS_STAGE_LIST = "ZQ_JC_INNERODDS_STAGE_LIST"; //竞彩期次
//	public static final String ZQ_TODAYMATCH_ISUPDATE = "ZQ_TODAYMATCH_ISUPDATE"; //当天比赛信息是否更新到db的标识   Map<String,String>

//	public static final String ZU_TOTAL_TEAM_TECH_PRE = "ZQ_TOTAL_TEAM_TECH";    //足球球队total技术  如: ZU_TOTAL_TEAM_TECH
//Map<String, Map<String, String>> , Map<teamQtId,Map<propertyName,propertyValue>>
//详细用法见TeamTechDataSpider
//	public static final String ZU_HOME_TEAM_TECH_PRE = "ZQ_HOME_TEAM_TECH_";	  //主队技术，同上

//	public static final String ZU_GUEST_TEAM_TECH_PRE = "ZQ_GUEST_TEAM_TECH_";    //客队技术，同上	

//	public static final String ZU_TOTAL_PLAYER_TECH_PRE = "ZU_TOTAL_PLAYER_TECH_"; //足球球员total技术前缀，_#{leagueId)
//Map<String, Map<String, String>> , Map<playerQtId,Map<propertyName,propertyValue>>

//	public static final String ZU_HOME_PLAYER_TECH_PRE = "ZU_HOME_PLAYER_TECH_";   //球员主队技术前缀，同上

//	public static final String ZU_GUEST_PLAYER_TECH_PRE = "ZU_GUEST_PLAYER_TECH_"; //球员客队技术前缀，同上

//	public static final String ZU_TEAM_CHARACTER = "ZU_TEAM_CHARACTER"; 		    //球队特点  

//	public static final String ZU_MATCH_FOREST = "ZU_MATCH_FOREST";				//球队的伤停缓存   Map<String,MatchForecast> 这是一个大Map,<matchQtId,MatchForecast>
