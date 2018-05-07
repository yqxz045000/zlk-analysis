package com.cfyj.zlk.football.analysis.example.converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfyj.zlk.football.analysis.example.dao.ExampleDao;
import com.cfyj.zlk.football.analysis.example.domain.OddsHundred;
import com.cfyj.zlk.football.entity.Odds;
import com.cfyj.zlk.football.utils.OddsUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataConverter {

	@Autowired
	private ExampleDao exampleDao;
	
	
	
	/**
	 * 赔率转换器
	 */
	@Transactional
	public void oddsConverter() {
		
		List<OddsHundred> data = exampleDao.findAll();
		List<Odds> list = new ArrayList<>(40000);
		if(data!=null && data.size()>0) {
			log.info("将解析的数据长度为：{}",data.size());
			for(OddsHundred odds_db: data) {
				if(StringUtils.isBlank(odds_db.getOdds_new()) ||  StringUtils.isBlank(odds_db.getOdds_old()) || StringUtils.isBlank(odds_db.getOdds_all())) {
					log.info("赔率数据不存在，跳过此条数据，数据项为：{}",odds_db);
					continue;
				}
				Odds odds = new Odds();
				odds.setNewOdds(parseOddsJson(odds_db.getOdds_new()));
				odds.setOldOdds(parseOddsJson(odds_db.getOdds_old()));
				
				String all = odds_db.getOdds_all();				
				StringBuffer sb = new StringBuffer();
				JSONArray ja = JSON.parseArray(all);
				for(int i=0;i<ja.size();i++ ) {
					JSONObject jsonObject = ja.getJSONObject(i);
					String  yz= parseOddsJson2(jsonObject.toJSONString());
					sb.append(yz);
				}
				odds.setAllOdds(sb.toString());
				odds.setCreateTime(new Timestamp(odds_db.getCreate_time().getTime()));
				odds.setUpdateTime(new Timestamp(odds_db.getUpdate_time().getTime()));
				odds.setQtid(odds_db.getMatch_qt_id());
				odds.setCompanyId(odds_db.getProvider_id().toString());
				odds.setType(1);
				list.add(odds);
			}
		}
		
		log.info("解析后的数据长度为：{}",list.size());
		int size = 1000;
		int num = (list.size()/size) +1;
		int recordSum = 0 ; 
		for(int i=0;i<num;i++) {
			int begin = i*size;
			int end = (i+1)*size;
			if(i==num-1) {
				begin = i*size;
				end = list.size();
			}
			log.info("第" +(i+1)+"次执行批量操作");
			List<Odds> subList = list.subList(begin, end);			
			recordSum = recordSum+ exampleDao.insertbatch(subList);
		}		
		
		log.info("入库长度：{}",recordSum);
		
	}
	
	
	public String parseOddsJson(String jsonStr) {
		StringBuffer  result = new StringBuffer();
		if(StringUtils.isNoneBlank(jsonStr)) {
			JSONArray ja = JSON.parseArray(jsonStr);
			String odds [] = new String [3];
			odds[0] = ja.getJSONObject(0).getString("p1"); //主
			odds[1] = ja.getJSONObject(0).getString("p2");//平
			odds[2] = ja.getJSONObject(0).getString("p3");//客胜
			result.append(odds[0]).append(",");
			result.append(odds[1]).append(",");
			result.append(odds[2]).append(",");
			int fhl = OddsUtil.calculationFhl(odds[0], odds[1], odds[2]);
			result.append(fhl);			
		}

		return result.toString();
	}
	
	
	public String parseOddsJson2(String jsonStr) {
		StringBuffer  result = new StringBuffer();
		if(StringUtils.isNoneBlank(jsonStr)) {
			JSONObject ja = JSON.parseObject(jsonStr);
			String odds [] = new String [4];
			odds[0] = ja.getString("p1"); //主
			odds[1] = ja.getString("p2");//平
			odds[2] = ja.getString("p3");//客胜
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
