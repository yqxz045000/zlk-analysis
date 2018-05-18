package com.cfyj.zlk.football.data.spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.constant.URLConstant;
import com.cfyj.zlk.football.data.parser.JCOfficialMatchParser;
import com.cfyj.zlk.football.data.parser.JCOfficialMatchStageParser;
import com.cfyj.zlk.football.data.service.StageMatchService;
import com.cfyj.zlk.football.data.service.StageService;
import com.cfyj.zlk.football.entity.Stage;
import com.cfyj.zlk.football.entity.Stage.StageType;
import com.cfyj.zlk.football.entity.StageMatch;

import lombok.extern.slf4j.Slf4j;

/**
 * 竞彩官方比赛
 * @author Exception
 *
 */
@Component
@Slf4j
public class JCOfficialMatchSpider extends BaseSpider {
	
	@Autowired
	private JCOfficialMatchStageParser stageParse;

	@Autowired
	private JCOfficialMatchParser jCMatchParse;

	@Autowired
	private StageMatchService stageMatchService;

	@Autowired
	private StageService stageService;
	
	
	@Override
	public void spiderData() throws Exception {
		List<Stage> stagelist = spider(URLConstant.JC_OFFICIAL_URL, StageType.JC, stageParse);
		if (stagelist != null) {
			stagelist = stageService.saveCurrent(stagelist, StageType.JC);
			Map<String, Long> map = new HashMap<String, Long>();
			for (Stage stage : stagelist) {
				map.put(stage.getName(), stage.getId());
			}

			List<StageMatch> smlist = spider(URLConstant.JC_OFFICIAL_URL, map, jCMatchParse);
			if (smlist != null) {
				for (StageMatch sm : smlist) {
					try {
						stageMatchService.saveOrUpdate(sm);
					} catch (Exception e) {
						log.error("error--hn:{},gn:{},time:{}", sm.getHn(), sm.getGn(), sm.getMatchTime(), e);
					}
				}	
//				cacheClient.set(CachePreConstant.ZQ_JC_INNERMATCH_LIST, smlist,TimeConstant.DAY_2);
			}
//			List<StageMatch> smlist_his2 =stageMatchService.getHistory(StageType.JC.ordinal(),2);
//			cacheClient.set(CachePreConstant.ZQ_JC_HISTORY2MATCH_LIST, smlist_his2,TimeConstant.DAY_2);
			
		}else {
			log.info("未爬取到主站竞彩足球期次数据");
		}

	}
	

}
