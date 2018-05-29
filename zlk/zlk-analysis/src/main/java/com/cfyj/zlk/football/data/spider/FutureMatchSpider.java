package com.cfyj.zlk.football.data.spider;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.constant.CharsetConstant;
import com.cfyj.zlk.football.constant.MatchConstant;
import com.cfyj.zlk.football.constant.URLConstant;
import com.cfyj.zlk.football.data.parser.FutureMatchParse;
import com.cfyj.zlk.football.data.service.LeagueService;
import com.cfyj.zlk.football.data.service.MatchService;
import com.cfyj.zlk.football.data.service.TeamService;
import com.cfyj.zlk.football.entity.League;
import com.cfyj.zlk.football.entity.Match;
import com.cfyj.zlk.football.entity.Team;
import com.cfyj.zlk.football.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 爬取未来赛程
 * 
 * @author ls
 * @2018年1月15日
 */
@Component
@Slf4j
public class FutureMatchSpider extends BaseSpider {
	


	@Autowired
	private FutureMatchParse futureMatchParse;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private LeagueService leagueService;

	@Override
	public void spiderData() throws Exception {
		
		for (int i = -1; i < 15; i++) {
			Thread.currentThread().sleep(5000);  
			DateTime date =  new DateTime().plusDays(i);
			String timeStr = DateUtil.dateToLong4SdfDate(date.toDate());
			
			log.info("抓取未来比赛======" + timeStr);
			List<Match> list = null;
			try {
				list = spider(URLConstant.QT_FUTUREMATCH_URL+timeStr+ ".htm", date.getYear()+"", futureMatchParse,CharsetConstant.CHARSET_GBK);			
				if(list==null) {
					log.info("爬取未来赛程无数据，爬取日期为：{}",date);
					continue;
				}
			} catch (Exception e) {
				log.error("爬取未来赛程异常，爬取日期为：{}"+date,e);
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
					} else {
						if (!DateUtils.isSameInstant(oldMatch.getMatchTime(), match.getMatchTime())
								||( StringUtils.isNotBlank(match.getGuestRank()) && ! match.getGuestRank().equals(oldMatch.getGuestRank()))
								||( StringUtils.isNotBlank(match.getHomeRank()) && ! match.getHomeRank().equals(oldMatch.getHomeRank()))) {
//							oldMatch.setMatchType(match.getMatchType());// 延期的比赛要更新比赛状态
//							oldMatch.setFullScore(match.getFullScore());// 清除“推迟|推遲|Delay”
							if (!StringUtils.isEmpty(match.getHomeRank())
									&& !StringUtils.isEmpty(match.getGuestRank())) {
								oldMatch.setHomeRank(match.getHomeRank());
								oldMatch.setGuestRank(match.getGuestRank());
							}
							oldMatch.setMatchTime(match.getMatchTime());			
							matchService.update(oldMatch);
						}
					}
				} catch (Exception e) {
					log.error("抓取未来赛事异常,比赛为{},比赛时间{}",match.getHn()+"vs"+match.getGn(),match.getMatchTime(), e);
				}	
			}
			
		}

	}

}
