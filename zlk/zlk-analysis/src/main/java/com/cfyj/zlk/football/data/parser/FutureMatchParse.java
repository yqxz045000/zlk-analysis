package com.cfyj.zlk.football.data.parser;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.entity.Match;
import com.cfyj.zlk.football.utils.ParseUtil;
/**
 * 解析未来赛程数据
 * @author ls
 * @2018年1月15日
 */
@Component

public class FutureMatchParse implements BaseParser<List<Match>>{
	private Logger log = LoggerFactory.getLogger(FutureMatchParse.class);

	private DateFormat format1 = new SimpleDateFormat("yyyy-M-d HH:mm");
	private DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<Match> parse(FetchedPage resp, Object param) throws Exception {
		List<Match> list = new ArrayList<Match>();
		String respStr = "";
		String year = (String) param;
		log.debug("爬虫返回的数据：" + resp.getContent());
		if (ParseUtil.checkResponseDataIsStr(resp.getContent())) {
			respStr = (String) resp.getContent();
		} else {
			return list;
		}
		
		Document doc = Jsoup.parse(respStr);
		Element tableEle= doc.getElementById("table_live");
		if(tableEle==null) {
			return list;
		}
		Elements trEle=tableEle.getElementsByTag("tr");
		for(int i=1;i<trEle.size();i++){
			Match match=new Match();
			Elements tdEle=trEle.get(i).getElementsByTag("td");
			String temp=tdEle.get(7).getElementsByTag("a").get(0).attr("onclick").split("\\(")[1];
			String id=temp.substring(0,temp.length()-1);
			match.setHn(tdEle.get(3).ownText());
			match.setGn(tdEle.get(5).ownText());
			match.setHomeRank(tdEle.get(3).getElementsByTag("font").text().replaceAll("[\\[\\]]", ""));
			match.setGuestRank(tdEle.get(5).getElementsByTag("font").text().replaceAll("[\\[\\]]", ""));
//			match.setMatchType(0);  默认为null，当天比赛接口还会更新一次记录
			match.setQtId(Long.valueOf(id));
			match.setLeagueName(tdEle.get(0).text());
			String newyear = year;
			try {
				DateTime begin = new DateTime(year + "-01-01T00:00:00");
				DateTime end = new DateTime(year + "-01-01T11:30:00");
				//计算特定日期是否在该区间内
				Interval between = new Interval(begin, end);
				String ownText = tdEle.get(1).ownText();
				boolean contained = between.contains(new DateTime(year + "-" + ownText.replace(" ", "T")));
		        if (contained) {
		        	newyear = String.valueOf(Integer.valueOf(year) + 1);
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
 			Date date=format1.parse(newyear+"-"+tdEle.get(1).ownText());
 			match.setMatchTime(format2.parse(format2.format(date)));
			list.add(match);
		}
		return list;
		
	}

}
