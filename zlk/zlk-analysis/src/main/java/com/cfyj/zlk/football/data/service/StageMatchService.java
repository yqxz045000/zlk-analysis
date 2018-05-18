package com.cfyj.zlk.football.data.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.MatchMapper;
import com.cfyj.zlk.football.data.dao.StageMatchMapper;
import com.cfyj.zlk.football.entity.StageMatch;
import com.cfyj.zlk.football.utils.DateUtil;

@Service

public class StageMatchService {

	private Logger log = LoggerFactory.getLogger(StageMatchService.class);

	@Autowired
	private StageMatchMapper stageMatchMapper;

	@Autowired
	private MatchMapper matchMapper;

	@Autowired
	private TeamService teamService;

	/**
	 * 1.保存stageMatch 2.和matches表进行匹配
	 * 
	 * @param sm
	 * @throws Exception
	 */
	public StageMatch saveOrUpdate(StageMatch sm) throws Exception {
		StageMatch oldStageMatch = stageMatchMapper.findBySortAndStageId(sm.getSort(), sm.getStageId());
		if (oldStageMatch != null) {
			if (!oldStageMatch.getHn().equals(sm.getHn()) || !oldStageMatch.getGn().equals(sm.getGn())) {
				oldStageMatch.setMatchId(null);
			}
			sm.setId(oldStageMatch.getId());
			sm.setMatchId(oldStageMatch.getMatchId());
			sm.setSwapTeam(oldStageMatch.getSwapTeam());
			if (sm.getMatchId() == null) { // 手动建立和match表的关系后将不进行匹配
				if(getMatchId(sm)) {
					sm.setSwapTeam(true);
				}
			}
			stageMatchMapper.updateStageMatchByInner(sm);
		} else {
			sm.setCreateTime(new Date());
			sm.setUpdateTime(new Date());
			sm.setAvailable(true);
			sm.setStatus(true);
			if(getMatchId(sm)) {
				sm.setSwapTeam(true);
			}else {
				sm.setSwapTeam(false);
			}
//			sm.setMatchId(getMatchId(sm));
			stageMatchMapper.insert(sm);
		}
		setQtid(sm);
		return sm;
	}
	
	
	
	public StageMatch getBySortAndStageId(int sortId,long stageId) {		
		return stageMatchMapper.findBySortAndStageId(sortId, stageId);
	}
	
	

	/**
	 * 为匹配成功的stageMatch设置qtid
	 * 
	 * @param sm
	 */
	public void setQtid(StageMatch sm) {
		if (sm != null && sm.getMatchId() != null) {
			Long qtId = matchMapper.findQtidById(sm.getMatchId());
			sm.setQtid(qtId);
		}
	}

	/**
	 * 历史2期的比赛数据
	 * 
	 * @param type
	 * @return
	 */
	public List<StageMatch> getHistory(int type,int num) {
		return stageMatchMapper.findHistory(type,num);
	}
	
	/**
	 * 1.根据主站的主客队名字取team表匹配别名，然后根据匹配的球队和比赛时间取matches表匹配对应的比赛
	 * 2.根据主站的主客队名字匹配team表，只要主客队有一个匹配成功，则拿匹配成功teamId和MatchTime匹配matches表
	 * @param stageMatch
	 * @param reverse  是否反转执行的标识
	 * @return
	 * @throws Exception
	 */
	private Long matchingMatch(StageMatch stageMatch, boolean reverse) throws Exception {
		if (stageMatch.getMname().equals("亚青赛")) {
			stageMatch.setHn(rename(stageMatch.getHn()));
			stageMatch.setGn(rename(stageMatch.getGn()));
		}

		String hn = "";
		String gn = "";
		String matchTime = getMatchTime(stageMatch);
		if (reverse) {
			hn = stageMatch.getGn();
			gn = stageMatch.getHn();
		} else {
			hn = stageMatch.getHn();
			gn = stageMatch.getGn();
		}
		
		List<Long> list = stageMatchMapper.matchingStageMatch(hn, gn, matchTime);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			log.warn("主站比赛匹配球探网比赛失败,比赛为:{},比赛时间:{}", hn + " vs " + gn, stageMatch.getMatchTime());
			return null;
		}
	}
	
	/**
	 * 只进行一重匹配，匹配时间短，
	 * @param stageMatch
	 * @return
	 * @throws Exception
	 */
	public boolean getMatchId(StageMatch stageMatch) throws Exception {
		Long begin = System.currentTimeMillis();
		boolean flag = false;

		Long matchId = matchingMatch2(stageMatch,false);
		stageMatch.setMatchId(matchId);
		
		Long end = System.currentTimeMillis();
		log.info("足球主站数据匹配球探数据耗时为：{} ms,比赛为:{},比赛时间:{} ",end-begin, stageMatch.getHn() + " vs " + stageMatch.getGn(), stageMatch.getMatchTime());
		return flag;		
	}
	
	/**
	 * 1.增加反转匹配
	 * 2.加长比赛时间范围匹配
	 * 但耗时比getMatchId更长
	 * @param stageMatch
	 * @return
	 * @throws Exception
	 */
	public boolean getMatchId2(StageMatch stageMatch) throws Exception {
		Long begin = System.currentTimeMillis();
		boolean flag = false;
		
		Long matchId = matchingMatch2(stageMatch,false);
		if(matchId==null) {
			//反转匹配
			matchId = matchingMatch2(stageMatch,true);
			if(matchId!=null) {
				flag = true;				
			}
		}
		stageMatch.setMatchId(matchId);
		
		Long end = System.currentTimeMillis();
		log.info("足球主站数据匹配球探数据耗时为：{} ms,比赛为:{},比赛时间:{} ",end-begin, stageMatch.getHn() + " vs " + stageMatch.getGn(), stageMatch.getMatchTime());
		return flag;		
	}
	
	
	private Long matchingMatch2(StageMatch stageMatch,boolean reverse) throws Exception {
		if (stageMatch.getMname().equals("亚青赛")) {
			stageMatch.setHn(rename(stageMatch.getHn()));
			stageMatch.setGn(rename(stageMatch.getGn()));
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(stageMatch.getMatchTime());	
		String hn = "";
		String gn = "";
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR)-2);
		String beginTime = DateUtil.dateToShortDate(c.getTime());
		
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR)+3);
		String endTime = DateUtil.dateToShortDate(c.getTime());
		if(reverse) {
			hn = stageMatch.getGn();
			gn = stageMatch.getHn();
		}else {
			hn = stageMatch.getHn();
			gn = stageMatch.getGn();
		}
		
		List<Long> list = stageMatchMapper.matchingStageMatch5(hn, gn,beginTime,endTime);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			// 去team表根据主客队名称查找teamId，只要有一个存在则根据teamId和matchTime匹配match表
			Long hTeamId = teamService.matchingTeamByTeamName2(hn);
			Long gTeamId = teamService.matchingTeamByTeamName2(gn);
			if (hTeamId != null || gTeamId != null) {
				list = stageMatchMapper.matchingStageMatch6(hTeamId, gTeamId, beginTime,endTime);
			}
			if (list != null && list.size() == 1) {
				return list.get(0);
			} else {
				log.warn("主站比赛匹配球探网比赛失败,比赛为:{},比赛时间:{}", hn + " vs " + gn,
						stageMatch.getMatchTime());
				return null;
			}
		}
	}

	/**
	 * 因为球探网的 00:00:00比赛显示时间为 day-1 23:59:00,需要重新设置比赛时间
	 * 
	 * @param stageMatch
	 * @throws Exception
	 */
	private String getMatchTime(StageMatch stageMatch) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(stageMatch.getMatchTime());
		int hh = c.get(Calendar.HOUR_OF_DAY);
		int mm = c.get(Calendar.MINUTE);
		if (hh == 0 && mm == 0) {
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
		}

		return DateUtil.dateToShortDate(c.getTime());
	}

	/**
	 * 匹配match
	 * 
	 * @param sm
	 * @throws Exception
	 */
	public void matchingMatchId(StageMatch sm) throws Exception {
		if (sm.getMatchId() == null) {
			// 匹配
			getMatchId(sm);
			if (sm.getMatchId() != null) {
				Long qtId = matchMapper.findQtidById(sm.getMatchId());
				sm.setQtid(qtId);
			}
		}
	}

	public static String rename(String name) {
		String numStr = name.substring(name.length() - 2, name.length());
		if (name.indexOf("U") > 0) {

		} else if (Pattern.matches("^[0-9]*$", numStr)) {
			String strA[] = name.split(name.charAt(name.length() - 2) + "");
			return strA[0] + "U" + numStr;
		}
		return name;
	}

	public StageMatch getByQtid(Long qtId) {
		return stageMatchMapper.findByQtid(qtId);
	}

	public StageMatch findByMatchId(Long matchId) {
		return stageMatchMapper.findByMatchId(matchId);
	}

	public List<StageMatch> getMatchingFailMatch() {
		return stageMatchMapper.findMatchingFailMatch();
	}

	/**
	 * 实体中matchId存在则更新，不存在则不更新
	 * 
	 * @param sm
	 */
	public void updateMatchId(StageMatch sm) {
		if (sm == null || sm.getMatchId() == null) {
			log.warn("StageMatch比赛数据为null或matchId不存在");
			return;
		}
		log.debug("stageMatch匹配matchId成功，数据为：{}", sm);
		stageMatchMapper.updateMatchId(sm);
	}

	/**
	 * 根据sort 和stageId寻找对应的matchId填充到stageMatch中
	 * 
	 * @param smlist_wc
	 */
	public void fillStageMatch(List<StageMatch> stageMatch_list) {

		for (StageMatch sm : stageMatch_list) {
			try {
				StageMatch db_stageMatch = stageMatchMapper.findStageMatchForMatching(sm.getSort(), sm.getStageId());
				if (db_stageMatch != null) {
					sm.setId(db_stageMatch.getId());
					sm.setMatchId(db_stageMatch.getMatchId());
					sm.setQtid(db_stageMatch.getQtid());
				}
			} catch (Exception e) {
				log.error("填充matchId异常");
			}
		}
	}



	public List<StageMatch> getCurrentSaleMatch() {
		// TODO Auto-generated method stub
		return null;
	}

}
