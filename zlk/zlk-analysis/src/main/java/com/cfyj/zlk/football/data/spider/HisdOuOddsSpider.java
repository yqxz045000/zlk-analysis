package com.cfyj.zlk.football.data.spider;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cfyj.zlk.football.constant.URLConstant;
import com.cfyj.zlk.football.data.parser.HisOuOddsParser;
import com.cfyj.zlk.football.data.service.MatchService;
import com.cfyj.zlk.football.data.service.OuOddsService;
import com.cfyj.zlk.football.domain.OddsHundred;
import com.cfyj.zlk.football.entity.Match;
import com.cfyj.zlk.football.entity.Odds;
import com.cfyj.zlk.football.utils.OddsUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HisdOuOddsSpider extends BaseSpider {
	private final static int  limitNum  =10000;
	private List<Match> failList = new ArrayList();
	int sum = 0;

	@Autowired
	private HisOuOddsParser hisOuOddsParser;
	
	@Autowired
	private OuOddsService oddsService;
	
	@Autowired
	private MatchService matchService;

	@Override
	public void spiderData() throws Exception {
		
		int num = matchService.count();
//		List<String> failList = new ArrayList<>();
		List<Match> matchList= null;
		int continueNum = num/limitNum + 1;
		for(int i =105;i<continueNum ; i++) {  //1482670
			int begin = i*limitNum;
			int end = limitNum;
			log.info("i-----------------:"+i);
			matchList = matchService.getByLimit(begin,end);
			if(matchList==null || matchList.size()==0) {
				continue;
			}
			spiderHisOdds(matchList);
			
		}
		
		if(failList!=null && failList.size()>0) {
			log.info("执行请求失败的列表，长度：{}",failList.size());
			matchList = new ArrayList();
			matchList.addAll(failList);
			failList = new ArrayList(); 
			spiderHisOdds(matchList);
		}
 		log.info("保存成功赔率size：{}",sum);
		log.info("失败比赛size：{}",failList.size());
		JSON.writeJSONString(new FileOutputStream("E:\\software\\workspace\\sts3\\zlk-analysis\\fail.text"), failList, SerializerFeature.PrettyFormat);
	}

	
	public void spiderHisOdds(List<Match> matchList) {
		for(Match match: matchList) {
//			Thread.sleep(5000);
			List<Odds> data = null;
			try {
				List<OddsHundred> list = spider(URLConstant.QT_HIS_OU_ODDS_URL + match.getQtId().toString() + ".js", match, hisOuOddsParser);
				if(list==null || list.size()==0) {
					continue;
				}
				log.info("解析后长度：{}", list.size());
				data = oddsConverter(list, match);
				
			} catch (Exception e) {
				log.error("解析失败",e);
				failList.add(match);
			}
			if(data!=null && data.size()>0) {
				for (Odds odds : data) {
					try {
						oddsService.saveOrUpdate(odds);
						sum ++;
					} catch (Exception e) {
						log.error("保存解析后的赔率异常，赔率数据为：{}", odds, e);
					}
				}
				
			}
		}
	}
	
	
	
	
	
	
	
	public List<Odds> oddsConverter(List<OddsHundred> data,Match match ) {
		
		List<Odds> list = new ArrayList<>(40000);
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
//		log.info("解析后的数据长度为：{}", list.size());
//		int size = 1000;
//		int num = (list.size() / size) + 1;
//		int recordSum = 0;
//		for (int i = 0; i < num; i++) {
//			int begin = i * size;
//			int end = (i + 1) * size;
//			if (i == num - 1) {
//				begin = i * size;
//				end = list.size();
//			}
//			log.info("第" + (i + 1) + "次执行批量操作");
//			List<Odds> subList = list.subList(begin, end);
//			recordSum = recordSum + exampleDao.insertbatch(subList);
//		}
//
//		log.info("入库长度：{}", recordSum);

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
