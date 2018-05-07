package com.cfyj.zlk.football.data.spider;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.constant.CharsetConstant;
import com.cfyj.zlk.football.constant.MatchConstant;
import com.cfyj.zlk.football.constant.URLConstant;
import com.cfyj.zlk.football.data.parser.FinshMatchParser;
import com.cfyj.zlk.football.data.service.LeagueService;
import com.cfyj.zlk.football.data.service.MatchService;
import com.cfyj.zlk.football.data.service.TeamService;
import com.cfyj.zlk.football.entity.League;
import com.cfyj.zlk.football.entity.Match;
import com.cfyj.zlk.football.entity.Team;
import com.cfyj.zlk.football.utils.DateUtil;
import com.cfyj.zlk.football.utils.MatchStateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 爬取已完赛的赛果
 * @author Exception
 *
 */
@Component
@Slf4j
public class FinshMatchSpider extends BaseSpider  {
	
	@Autowired
	private FinshMatchParser finshMatchParser;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private LeagueService leagueService;
	

	@Override
	public void spiderData() throws Exception {
		
		Date beginDate = DateUtil.Long4StringToDate("20140714");
		Date endDate = DateUtil.getBeforDate(new Date(),2);
		DateTime dateTime = new DateTime(beginDate);
		int matchSum = 0;
		int diffentDate =  DateUtil.differenceDay(endDate,beginDate);
		log.info("爬取完赛列表，开始时间：{},结束时间:{},总天数为：{}",DateUtil.dateToLong4SdfDate(beginDate),DateUtil.dateToLong4SdfDate(endDate),diffentDate);
		for(int i=0;i<=diffentDate;i++) {
//			Thread.currentThread().sleep(10000);
			DateTime spiderCurrentDate = dateTime.plusDays(i);
			String dateStr = DateUtil.dateToLong4SdfDate(spiderCurrentDate.toDate());

			log.info("抓取完赛赛果======" + dateStr);
			List<Match> list = null;
			try {
				list = spider(URLConstant.QT_FINISHMATCH_URL+dateStr+ ".htm", spiderCurrentDate,finshMatchParser,CharsetConstant.CHARSET_GBK);			
				if(list==null) {
					log.info("爬取未来赛程无数据，爬取日期为：{}",dateStr);
					continue;
				}					
			} catch (Exception e) {
				log.error("爬取未来赛程异常，爬取日期为：{}"+dateStr,e);
				continue;
			}
			
			
			for (Match match : list) {
				
				try {
					Match oldMatch = matchService.getMatchByQtId(match.getQtId());
					if (oldMatch == null) {
						match.setId(match.getQtId() + MatchConstant.MATCHID_PRE);
						// 赛程赛果没抓到的比赛，即时比分抓到的，添加主客队ID 联赛ID 赛季ID
						List<Team> homeTeam = teamService.findByCnName(match.getHn());
						if (homeTeam != null && homeTeam.size() > 0) {
							match.setHid(homeTeam.get(0).getQtId());
						}
						List<Team> gusetTeam = teamService.findByCnName(match.getGn());
						if (gusetTeam != null && gusetTeam.size() > 0) {
							match.setGid(gusetTeam.get(0).getQtId());
						}
						List<League> league = leagueService.findByShortNameCn(match.getLeagueName());
						if (league != null && league.size() > 0) {
							match.setLeagueId(league.get(0).getId());
						}

						if (match.getFullScore() != null && match.getFullScore().indexOf("|") != -1) {// 腰斩|腰斬|Interupt,取消|取消|Cancel,推迟|推遲|Delay,中断|中斷|Suspend,待定|待定|Pend.
							match.setFullScore(match.getFullScore().substring(0, match.getFullScore().indexOf("|")));
						}
						matchService.save(match);
					}else if(  StringUtils.isBlank(oldMatch.getFullScore()) ||  StringUtils.isBlank(oldMatch.getHalfScore()) || !oldMatch.getFullScore().equals(match.getFullScore()) || !oldMatch.getHalfScore().equals(match.getHalfScore())) { 
//					else if(oldMatch.getMatchType()==null || !MatchStateUtil.QT_MATCHSTATE_WC.equals(oldMatch.getMatchType().toString()) ){					
						//DB的状态为未完赛的则更新比分和状态						
						matchService.updateScoreAndstate(match);			
					
					}
					matchSum++;
				} catch (Exception e) {
					log.error("抓取未来赛事异常,比赛为{},比赛时间{}",match.getHn()+"vs"+match.getGn(),match.getMatchTime(), e);
				}
				
			
			}

		}
		
		log.info("爬取完赛列表，爬取场次为：{} , 开始时间：{},结束时间:{},总天数为：{}",matchSum,DateUtil.dateToLong4SdfDate(beginDate),DateUtil.dateToLong4SdfDate(endDate),diffentDate);
		
	}

}
