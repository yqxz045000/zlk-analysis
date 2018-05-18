package com.cfyj.zlk.football.data.spider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfyj.zlk.football.constant.URLConstant;
import com.cfyj.zlk.football.data.parser.OuOddsParser;
import com.cfyj.zlk.football.data.service.MatchService;
import com.cfyj.zlk.football.data.service.OuOddsService;
import com.cfyj.zlk.football.data.service.StageMatchService;
import com.cfyj.zlk.football.domain.OddsHundred;
import com.cfyj.zlk.football.domain.OddsMatchVO;
import com.cfyj.zlk.football.entity.Odds;
import com.cfyj.zlk.football.utils.OddsUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 爬取竞彩官方开售比赛的欧赔
 * 
 * @author Exception
 *
 */
@Slf4j
@Component
public class OuOddsSpider extends BaseSpider {

	@Autowired
	private StageMatchService stageMatchService;

	@Autowired
	private MatchService matchService;
	
	@Autowired
	private OuOddsParser ouOddsParser; 
	
	@Autowired
	private OuOddsService oddsService;

	@Override
	public void spiderData() throws Exception {

		List<OddsMatchVO> saleList = matchService.getCurrentSaleMatch();

		if (saleList == null || saleList.size() == 0) {
			log.info("无正在开售的比赛，不爬取欧赔----");
			return;
		}

		for (OddsMatchVO om : saleList) {

			List<OddsHundred> list = spiderOdds(om);
			if (list == null || list.size() == 0) {
				continue;
			}else {
				log.info("爬取开售比赛赔率的长度：{}", list.size());
				List<Odds> data  = oddsConverter(list, om);
				if(data!=null && data.size()>0) {
					for (Odds odds : data) {
						try {
							oddsService.saveOrUpdate(odds);
						} catch (Exception e) {
							log.error("保存解析后的赔率异常，赔率数据为：{}", odds, e);
						}
					}
					
				}
				
			}

		}

	}
	
	public List<OddsHundred> spiderOdds(OddsMatchVO om){
		try {
			
			List<OddsHundred> list = spider(URLConstant.QT_HIS_OU_ODDS_URL + om.getQtid().toString() + ".js", om,
					ouOddsParser);
			return list;
		} catch (Exception e) {
			log.error("爬取开售比赛赔率异常",e);
		}
		return null;
	}
	
	
	public List<Odds> oddsConverter(List<OddsHundred> data,OddsMatchVO match ) {
		
		List<Odds> list = new ArrayList<>();
		if (data != null && data.size() > 0) {
			log.info("将解析的数据长度为：{}", data.size());
			for (OddsHundred odds_db : data) {
				try {
					if (StringUtils.isBlank(odds_db.getOddsNew()) || StringUtils.isBlank(odds_db.getOddsOld())
							|| StringUtils.isBlank(odds_db.getOddsAll())) {
						log.info("赔率数据不存在，跳过此条数据，数据项为：{}", odds_db);
						continue;
					}
					Odds odds = new Odds();
					odds.setNewOdds(parseOddsJson(odds_db.getOddsNew()));
					odds.setOldOdds(parseOddsJson(odds_db.getOddsOld()));
					
					String all = odds_db.getOddsAll();
					StringBuffer sb = new StringBuffer();
					JSONArray ja = JSON.parseArray(all);
					for (int i = 0; i < ja.size(); i++) {
						JSONObject jsonObject = ja.getJSONObject(i);
						String yz = parseOddsJson2(jsonObject.toJSONString());
						sb.append(yz);
					}
					odds.setAllOdds(sb.toString());
//				odds.setCreateTime(new Timestamp(odds_db.getCreate_time().getTime()));
//				odds.setUpdateTime(new Timestamp(odds_db.getUpdate_time().getTime()));
					odds.setQtid(odds_db.getMatchQtId());
					odds.setCompanyId(odds_db.getProviderId().toString());
					odds.setType(1);
					if(match.getMatchTime()!=null) {
						odds.setCreateTime(new Timestamp(match.getMatchTime().getTime()));	
						odds.setUpdateTime(new Timestamp(match.getMatchTime().getTime()));	
					}
					list.add(odds);
					
				} catch (Exception e) {
					log.error("转化赔率数据异常");
				}
			}
		}
		return list ;


	}

	public String parseOddsJson(String jsonStr) {
		StringBuffer result = new StringBuffer();
		if (StringUtils.isNoneBlank(jsonStr)) {
			JSONObject ja = JSON.parseObject(jsonStr.trim());
			String odds[] = new String[3];
			odds[0] = ja.getString("p1"); // 主
			odds[1] = ja.getString("p2");// 平
			odds[2] = ja.getString("p3");// 客胜
			result.append(odds[0]).append(",");
			result.append(odds[1]).append(",");
			result.append(odds[2]).append(",");
			int fhl = OddsUtil.calculationFhl(odds[0], odds[1], odds[2]);
			result.append(fhl);
		}

		return result.toString();
	}

	public String parseOddsJson2(String jsonStr) {
		StringBuffer result = new StringBuffer();
		if (StringUtils.isNoneBlank(jsonStr)) {
			JSONObject ja = JSON.parseObject(jsonStr);
			String odds[] = new String[4];
			odds[0] = ja.getString("p1"); // 主
			odds[1] = ja.getString("p2");// 平
			odds[2] = ja.getString("p3");// 客胜
			odds[3] = ja.getString("time");
			result.append(odds[0]).append(",");
			result.append(odds[1]).append(",");
			result.append(odds[2]).append(",");
			int fhl = OddsUtil.calculationFhl(odds[0], odds[1], odds[2]);
			result.append(fhl).append(",");
			result.append(odds[3]).append(";");
		}

		return result.toString();
	}


}
