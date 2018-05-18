package com.cfyj.zlk.football.data.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.domain.OddsHundred;
import com.cfyj.zlk.football.domain.OddsHundredJson;
import com.cfyj.zlk.football.domain.OddsMatchVO;
import com.cfyj.zlk.football.utils.ParseUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OuOddsParser implements BaseParser<List<OddsHundred>> {

	@Override
	public List<OddsHundred> parse(FetchedPage resp, Object param) throws Exception {

		log.info("解析器收到传回的响应数据，响应状态码：{},响应数据是否为null:{}，响应数据长度:{}", resp.getStatusCode(), resp.getContent() == null,
				resp.getContent() == null ? 0 : resp.getContent().length());
		List<OddsHundred> list = new ArrayList<OddsHundred>();
		String respStr = "";
		if (ParseUtil.checkResponseDataIsStr(resp.getContent())) {
			respStr = (String) resp.getContent();
		} else {
			return list;
		}
		OddsMatchVO om = (OddsMatchVO) param;
		Map<String, String> game_map = new HashMap<>();
		List<String> gamedetailList = new ArrayList<>();
		getGameStr(respStr, game_map, gamedetailList);
		parseGameDeail(game_map, gamedetailList, om, list);
		return list;
	}

	private void parseGameDeail(Map<String, String> game_map, List<String> gamedetailList,OddsMatchVO om, List<OddsHundred> result) {

		if (gamedetailList != null && gamedetailList.size() > 0 && game_map != null && game_map.size() > 0) {

			for (String gd : gamedetailList) {
				
//				String temp = gameDetail.get(i, gameDetail).toString();//78598697^2.53|3.04|2.63|04-30 17:46|0.82|0.97|0.94;2.25|2.95|3.11|04-29 22:08|0.73|0.94|1.12;2.24|2.95|3.13|04-29 19:42|0.72|0.94|1.12;
				try {
					gd = gd.replace("\"","");
					String[] strs = gd.split("\\^");//[78598697, 2.53|3.04|2.63|04-30 17:46|0.82|0.97|0.94;2.25|2.95|3.11|04-29 22:08|0.73|0.94|1.12;2.24|2.95|3.13|04-29 19:42|0.72|0.94|1.12;]
					OddsHundred hodds = new OddsHundred();
					hodds.setProviderId(Integer.valueOf(game_map.get(strs[0])));
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
						hodds.setMatchQtId(om.getQtid());  
						result.add(hodds);
					}
					
				} catch (Exception e) {
					log.error("包装赔率异常,数据:{}",gd,e);
				}
				
//				if (StringUtils.isNotBlank(gd)) {
//
//					String[] gd_split = gd.split(";");
//					for(String gd2: gd_split) {
//						String[] gd2_split = gd2.split("\\|");
//						if(gd2_split!=null && gd2_split.length>4) {
//							OddsHundred oh = new OddsHundred();												
//						}
//					}					
//				}
			}
		}
	}

	private void getGameStr(String content, Map<String, String> game_map, List<String> gamedetailList) {

		Map<String, String> map = new HashMap<>();
		String[] split_var = content.split("var");
		for (String str : split_var) {
			if (str.indexOf("game=") > -1) {
				// str.substring(str.indexOf("(")+1, str.indexOf(")")+1);
				map.put("game", str.substring(str.indexOf("(") + 1, str.indexOf(");") - 1));
			} else if (str.indexOf("gameDetail=") > -1) {
				map.put("gameDetail", str.substring(str.indexOf("(") + 1, str.indexOf(");") - 1));
			}
		}

		convertGameStr(map.get("game"), game_map);
		convertGameDetailStr(map.get("gameDetail"), gamedetailList);

	}

	private void convertGameDetailStr(String gameDetailStr, List<String> gamedetailList) {

		if (StringUtils.isNotBlank(gameDetailStr)) {
			String[] gameDetail_split = gameDetailStr.split(",");
			for (String str : gameDetail_split) {
				try {
					if (StringUtils.isNotBlank(str)) {
						str = str.replaceAll("\"", "");
						gamedetailList.add(str);
					}

				} catch (Exception e) {
					log.error("转化赔率详情异常,str:{}", str, e);
				}

			}

		}

	}

	private void convertGameStr(String gameContent, Map<String, String> game_map) {

		if (StringUtils.isNotBlank(gameContent)) {
			String[] game_split = gameContent.split("\",\"");
			for (String str : game_split) {
				String[] game_split2 = null;
				try {
					if (StringUtils.isNotBlank(str)) {
						str = str.replaceAll("\"", "");
						game_split2 = str.split("\\|");
						game_map.put(game_split2[1], game_split2[0]);
					}

				} catch (Exception e) {
					log.error("转化赔率详情异常,str:{}", str, e);
				}

			}
		}

	}

}
