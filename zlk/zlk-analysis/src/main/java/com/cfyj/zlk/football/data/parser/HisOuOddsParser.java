package com.cfyj.zlk.football.data.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.domain.OddsHundred;
import com.cfyj.zlk.football.domain.OddsHundredJson;
import com.cfyj.zlk.football.entity.Match;
import net.sf.json.JSONArray;
import lombok.extern.slf4j.Slf4j;
import sun.org.mozilla.javascript.internal.NativeArray;

@Component
@Slf4j
public class HisOuOddsParser implements BaseParser<List<OddsHundred>>{
	
	
	@Override
	public List<OddsHundred> parse(FetchedPage resp, Object param) throws Exception {
		//String content, Match match
		if(resp.getStatusCode()==404) {
			return null;
		}
		String content = (String) resp.getContent();
		Match match = (Match) param ; 
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		if(content.getBytes()[0]==-17&&content.getBytes()[1]==-65&&content.getBytes()[2]==-67){//当content以��开头时，要做一下处理
			System.out.println("以��开头的欧赔数据比赛球探id为"+match.getQtId());
			content=content.substring(2);
			if (content != null) {
		        int len = content.length();
		        if (len > 0) {
		            StringBuilder sb = new StringBuilder(len);
		            for (int i = 0; i < len; ++i) {
		                char c = content.charAt(i);
		                if (c!=0) {//剔除空字符
		                    sb.append(c);
		                }
		            }
			        content=sb.toString();
		        }
		    }
		}
		try {
			engine.eval(content);
		} catch (ScriptException e) {
			log.error("scriptException");
			if(content.indexOf("html")!=-1&&content.indexOf("404")!=-1){//返回错误内容，用于日志打印
				List<OddsHundred> list =new ArrayList<>();
				OddsHundred oddsHundred =new OddsHundred();
				oddsHundred.setOddsAll("404");
				list.add(oddsHundred);
				return  null;
			}else{//返回错误内容，用于日志打印
				List<OddsHundred> list =new ArrayList<>();
				OddsHundred oddsHundred =new OddsHundred();
				oddsHundred.setOddsAll(content);
				list.add(oddsHundred);
				return null;
			}
		}
		NativeArray game = (NativeArray) engine.get("game");
		Map<String, String> gameMap = new HashMap<String, String>();
		for (int i = 0; i < game.getLength(); i++) {
			String[] temp = game.get(i, game).toString().split("\\|");
			gameMap.put(temp[1], temp[0]);
		}
		List<OddsHundred> alllist = new LinkedList<OddsHundred>();
		NativeArray gameDetail = (NativeArray) engine.get("gameDetail");
		if (gameMap.isEmpty()&&gameDetail == null) {
			return null;
		}else if(gameDetail == null&&game!=null){//年代久远的历史数据没有gamedetail ,比如2006年等
			for (int i = 0; i < game.getLength(); i++) {
				String[] temp = game.get(i, game).toString().split("\\|");
				OddsHundred hodds = new OddsHundred();
				hodds.setProviderId(Integer.valueOf(temp[0]));
				List<OddsHundredJson> oddsJsonlist = new LinkedList<OddsHundredJson>();
				OddsHundredJson oddsJson = new OddsHundredJson();
				oddsJson.setP1(Double.valueOf(temp[3]));
				oddsJson.setP2(Double.valueOf(temp[4]));
				oddsJson.setP3(Double.valueOf(temp[5]));
				String[] times = temp[20].split(",");
				String time = times[1].split("-")[0]+"-"+times[2]+" "+times[3]+":"+times[4];
				oddsJson.setTime(time);
				oddsJsonlist.add(oddsJson);
				if (!oddsJsonlist.isEmpty()) {
					hodds.setOddsAll(JSON.toJSONString(oddsJsonlist));
					hodds.setOddsNew(JSON.toJSONString(oddsJsonlist.get(0)));
					hodds.setOddsOld(JSON.toJSONString(oddsJsonlist.get(oddsJsonlist.size() - 1)));
					hodds.setMatchQtId(match.getQtId());  
					alllist.add(hodds);
				}
			}
		}else if(gameDetail!=null){
			for (int i = 0; i < gameDetail.getLength(); i++) {
				String temp = gameDetail.get(i, gameDetail).toString();
				String[] strs = temp.split("\\^");
				OddsHundred hodds = new OddsHundred();
				hodds.setProviderId(Integer.valueOf(gameMap.get(strs[0])));
				String[] oddsTemp = strs[1].split(";");
				List<OddsHundredJson> oddsJsonlist = new LinkedList<OddsHundredJson>();
				for (int k = 0; k < oddsTemp.length; k++) {
					OddsHundredJson oddsJson = new OddsHundredJson();
					String[] ooo = oddsTemp[k].split("\\|");
					oddsJson.setP1(Double.valueOf(ooo[0]));
					oddsJson.setP2(Double.valueOf(ooo[1]));
					oddsJson.setP3(Double.valueOf(ooo[2]));
					oddsJson.setTime(ooo[3]);
					oddsJsonlist.add(oddsJson);
				}
				if (!oddsJsonlist.isEmpty()) {
					hodds.setOddsAll(JSON.toJSONString(oddsJsonlist));
					hodds.setOddsNew(JSON.toJSONString(oddsJsonlist.get(0)));
					hodds.setOddsOld(JSON.toJSONString(oddsJsonlist.get(oddsJsonlist.size() - 1)));
					hodds.setMatchQtId(match.getQtId());  
					alllist.add(hodds);
				}
			}
		}
		return alllist;
	}



}
