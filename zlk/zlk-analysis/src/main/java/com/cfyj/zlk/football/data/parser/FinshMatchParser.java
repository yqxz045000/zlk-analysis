package com.cfyj.zlk.football.data.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.entity.Match;
import com.cfyj.zlk.football.utils.DateUtil;
import com.cfyj.zlk.football.utils.MatchStateUtil;
import com.cfyj.zlk.football.utils.ParseUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FinshMatchParser implements BaseParser<List<Match>> {

	private DateFormat format1 = new SimpleDateFormat("yyyy-M-d HH:mm");
	private DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<Match> parse(FetchedPage resp, Object param) throws Exception {
		List<Match> list = new ArrayList<Match>();
		String respStr = "";
//		String year = (String) param;
		DateTime spiderCurrentDate = (DateTime) param ;
//		log.debug("爬虫返回的数据：" + respObj);
		if (ParseUtil.checkResponseDataIsStr(resp.getContent())) {
			respStr = (String) resp.getContent();
		} else {
			return list;
		}

		Document doc = Jsoup.parse(respStr);
		Element tableEle = doc.getElementById("table_live");
		if (tableEle == null) {
			return list;
		}
		Elements trEle = tableEle.getElementsByTag("tr");
		for (int i = 1; i < trEle.size(); i++) {
			
			Match match = new Match();
			Elements tdEle = trEle.get(i).getElementsByTag("td");
			try {
				if(tdEle.size()!=10) {
					log.warn("比赛数据不能解析，跳过数据，");
					continue;
				}else if(!tdEle.get(2).text().equals("完")) {
					log.warn("比赛不为已完赛,跳过数据");
					continue;
				}
				
				match.setLeagueName(tdEle.get(0).text());
				
				String  dayOfMonth = spiderCurrentDate.getDayOfMonth()+"";
				String pageMatchTime = tdEle.get(1).ownText();
				if(!equalsDay(dayOfMonth,pageMatchTime)) {
					spiderCurrentDate = spiderCurrentDate.plusDays(1);
				}
				String time  = DateUtil.dateToLong4SdfDate(spiderCurrentDate.toDate())+" " +pageMatchTime.split("日")[1];		
				match.setMatchTime(DateUtil.StringToDate(time, "yyyyMMdd HH:mm"));
				
				match.setMatchType(Integer.parseInt(MatchStateUtil.QT_MATCHSTATE_WC));
				match.setHn(tdEle.get(3).ownText());
				match.setGn(tdEle.get(5).ownText());
				match.setHomeRank(tdEle.get(3).getElementsByTag("font").text().replaceAll("[\\[\\]]", ""));
				match.setGuestRank(tdEle.get(5).getElementsByTag("font").text().replaceAll("[\\[\\]]", ""));
				
				Elements elementsByTag = tdEle.get(4).getElementsByTag("font");
				if(elementsByTag.size()==0) {
					continue;
				}
				String fullscore = elementsByTag.get(0).text()+"-"+ elementsByTag.get(1).text() ; 
				match.setFullScore(fullscore);
				
				Elements elementsByTag2 = tdEle.get(6).getElementsByTag("font");
				if(elementsByTag2.size()>1) {
					String halfscore = elementsByTag2.get(0).text()+"-"+ elementsByTag2.get(1).text() ; 
					match.setHalfScore(halfscore);
					
				}
				
				String temp = tdEle.get(9).getElementsByTag("a").get(0).attr("onclick").split("\\(")[1];
				String id = temp.substring(0, temp.length() - 1);		
				match.setQtId(Long.valueOf(id));
				
				list.add(match);
				
			} catch (Exception e) {
				log.error("解析完赛结果异常，数据项为:{}",tdEle.toString(),e);
			}
		}
		return list;
	}
	
	private boolean equalsDay(String day,String pageMatchTime){
		if(pageMatchTime.indexOf("日")==-1) {
			log.error("日期错误，不能转化:",pageMatchTime);
			throw new RuntimeException();
		}
		String pageMatchDay = pageMatchTime.split("日")[0];
		return pageMatchDay.trim().equals(day.trim()) ;
		
	}
	

}
