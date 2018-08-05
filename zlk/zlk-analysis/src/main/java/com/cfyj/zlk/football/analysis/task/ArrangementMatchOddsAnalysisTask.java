package com.cfyj.zlk.football.analysis.task;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.data.dao.MatchOddsResultMapper;
import com.cfyj.zlk.football.data.service.MatchService;
import com.cfyj.zlk.football.data.service.OuOddsService;
import com.cfyj.zlk.football.entity.Match;
import com.cfyj.zlk.football.entity.MatchOddsResult;
import com.cfyj.zlk.football.entity.Odds;
import com.cfyj.zlk.football.utils.ArithOddsUtil;
import com.cfyj.zlk.football.utils.ArithUtil;
import com.cfyj.zlk.football.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 统计比赛的基本赔率数据
 * 
 * @author Exception
 *
 */
@Component
@Slf4j
public class ArrangementMatchOddsAnalysisTask {

	@Autowired
	private MatchService matchService;

	@Autowired
	private OuOddsService ouOddsService;

	@Autowired
	private MatchOddsResultMapper matchOddsResultMapper;
	
	private final static int  limitNum  =5000;
	
	private boolean flag = true;
	
	//立博 82 ，威廉希尔 115，韦德 81，10BET 16，金宝博 499，bet365 281，betDAQ 54,澳门 80

	@Scheduled(fixedDelay=6000)
	public void arrangementMatchOddsAnalysisTask() throws Exception {
//		if(!flag) {
//			return ;
//		}
//		flag = false;
//		int count = matchService.countByAnalysisMatch();
//		int continueNum = count/limitNum + 1;
//		for(int i =111;i<continueNum ; i++) {  
//			int begin = i*limitNum;
//			int end = limitNum;
//			log.info("i-----------------:"+i);
//			List<Match> matchList = matchService.getAnalysisMatchByLimit(begin,end);
//			if(matchList==null || matchList.size()==0) {
//				continue;
//			}
//			
//			arrangementMatchOddsResult(matchList);
//			
//		}
		
//		List<Match> matchList = matchService.getAnalysisMatchByTime(DateUtil.Long4StringToDate("20170510"),DateUtil.Long4StringToDate("20180530"));
		log.info("更新比赛赔率结果开始");
		List<Match> matchList = matchService.getCurrentSaleMatch2();
		arrangementMatchOddsResult(matchList);
		log.info("更新比赛赔率结果结束");
	}

	private void arrangementMatchOddsResult(List<Match> mlist) {

		if (mlist != null && mlist.size() != 0) {
			for (ListIterator<Match> it = mlist.listIterator(); it.hasNext();) {

				Match match = it.next();
//				if(match==null || match.getQtId()==null||StringUtils.isBlank(match.getFullScore())||match.getFullScore().length()>4) {
//					continue;
//				}
				if(match==null || match.getQtId()==null) {
					continue;
				}
				List<Odds> oList = ouOddsService.getAnalysisByQtidAndCompanyId(match.getQtId());
				try {
					if (oList != null && oList.size() != 0) {
						
						List<MatchOddsResult> results = new ArrayList<>();
						for (ListIterator<Odds> it2 = oList.listIterator(); it2.hasNext();) {
							Odds odds = it2.next();
							try {
								MatchOddsResult mor = SetMatchOddsResult(odds, match);
								results.add(mor);
								if(matchOddsResultMapper.isExitByQqtidAndCompanyId(mor.getQtid(),mor.getCompanyId())==0) {
									matchOddsResultMapper.insert(mor);
								}else {
									matchOddsResultMapper.update(mor);
								}
							} catch (Exception e) {
								log.error("填充比赛赔率结果异常，原数据：{}", odds, e);
							}
							
						}
//						matchOddsResultMapper.batchInsert(results);
					}
					
				} catch (Exception e) {
					log.error("批量插入比赛赔率结果异常,比赛：{}",match,e);
				}

			}
		}

	}

	private MatchOddsResult SetMatchOddsResult(Odds odds, Match match) {
		MatchOddsResult mor = new MatchOddsResult();
//		if(StringUtils.isNotBlank(match.getFullScore())) {
			setbasicInfo(odds, match, mor);
			setOddsResult(odds, match, mor);			
//		}
		return mor;
	}

	private void setOddsResult(Odds odds, Match match, MatchOddsResult mor) {

		if (match != null && match.getQtId() != null) {
			// String[] matchResult = { "主胜", "平局", "客胜" };
			int[] matchResult = { 1, 2, 3 };
			String oddsA[] = odds.getNewOdds().split(",");//主 平 客
			String oddsA2[] = odds.getOldOdds().split(",");
			Double oddsrate[] = getOddsRate(oddsA);
			Double oddsrate2[] = getOddsRate(oddsA2);

//			Double oddskl1[] = getOddsKL(oddsA);
//			Double oddskl2[] = getOddsKL(oddsA2);
			//有比赛结果的则填充比赛结果的数据
			if(StringUtils.isNotBlank(match.getFullScore()) && match.getFullScore().length()>4) {
				int flag = 0;
				String[] split = match.getFullScore().split("-");
				int hsc = Integer.parseInt(split[0]);
				int gsc = Integer.parseInt(split[1]);
				if (hsc > gsc) {
					flag = 0;
				} else if (hsc == gsc) {
					flag = 1;
				} else {
					flag = 2;
				}
				mor.setMatchResult(matchResult[flag]);
				mor.setMatchResultRate(oddsrate[flag]);
				
			}

			mor.setOddsUpdateNum(odds.getAllOdds().split(";").length);
			mor.setNwinOddsRate(oddsrate[0]);	
			mor.setNdrawOddsRate(oddsrate[1]);
			mor.setNfailOddsRate(oddsrate[2]);
			mor.setOwinOddsRate(oddsrate2[0]);
			mor.setOdrawOddsRate(oddsrate2[1]);
			mor.setOfailOddsRate(oddsrate2[2]);

//			mor.setNwinOddsKl(oddskl1[0]);
//			mor.setNdrawOddsKl(oddskl1[1]);
//			mor.setNfailOddsKl(oddskl1[2]);
//			mor.setOwinOddsKl(oddskl2[0]);
//			mor.setOdrawOddsKl(oddskl2[1]);
//			mor.setOfailOddsKl(oddskl2[2]);
		}
	}

	/**
	 * 计算各赔率的胜率
	 * 
	 * @return
	 */
	private Double[] getOddsRate(String odds[]) {

		Double result[] = new Double[3];

		result[0] = ArithUtil.mul(ArithOddsUtil.arithWinRate2(odds[0], odds[1], odds[2]), "100"); // 主胜
		result[1] = ArithUtil.mul(ArithOddsUtil.arithWinRate2(odds[1], odds[0], odds[2]), "100");// 平
		result[2] = ArithUtil.mul(ArithOddsUtil.arithWinRate2(odds[2], odds[0], odds[1]), "100");// 客胜

		return result;
	}

	private void setbasicInfo(Odds odds, Match match, MatchOddsResult mor) {

		String[] newOdds = odds.getNewOdds().split(",");
		String[] oldOdds = odds.getOldOdds().split(",");
		mor.setNwinOdds(new Double(newOdds[0]));
		mor.setNdrawOdds(new Double(newOdds[1]));
		mor.setNfailOdds(new Double(newOdds[2]));
		mor.setOwinOdds(new Double(oldOdds[0]));
		mor.setOdrawOdds(new Double(oldOdds[1]));
		mor.setOfailOdds(new Double(oldOdds[2]));
		mor.setFhl(new Integer(newOdds[3]));

		mor.setQtid(match.getQtId());
		mor.setCompanyId(odds.getCompanyId());
		mor.setAllOdds(odds.getAllOdds());
		mor.setType(1);
		mor.setMatchType(match.getMatchType());
		mor.setFullScore(match.getFullScore());
		mor.setHalfScore(match.getHalfScore());
		// mor.setMatchResult();
		mor.setHn(match.getHn());
		mor.setGn(match.getGn());
		mor.setHid(match.getHid());
		mor.setGid(match.getGid());
		mor.setLeagueId(match.getLeagueId());
		mor.setMatchTime(match.getMatchTime());
		mor.setHr(match.getHomeRank());
		mor.setGr(match.getGuestRank());
	}

}
