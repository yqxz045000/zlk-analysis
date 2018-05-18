package com.cfyj.zlk.football.data.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.entity.StageMatch;

/**
 * 主站竞彩足球解析
 * @author ls
 * @2018年1月9日
 */
@Component
public class JCOfficialMatchParser implements BaseParser<List<StageMatch>>{
	
	private Logger log = LoggerFactory.getLogger(JCOfficialMatchParser.class);

	@Override
	public List<StageMatch> parse(FetchedPage respObj, Object param) throws Exception {
		List<StageMatch> smlist = new ArrayList<StageMatch>();
		String respStr = "";
		Map<String, Long> map = (Map<String, Long>) param;
		log.debug("爬虫返回的数据：" + respObj);
		if (checkResponseData(respObj.getContent())) {
			respStr = (String) respObj.getContent();
		} else {
			return smlist;
		}

		Document document = DocumentHelper.parseText(respStr);
		
		if (document != null) {
			smlist = new ArrayList<StageMatch>();
			Element root = document.getRootElement();
			List nodes = root.elements();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				try {
					StageMatch sm = new StageMatch();
					Element elm = (Element) it.next();
					sm.setStageId(map.get(elm.attributeValue("itemid").substring(0, 6)));
					String hn = elm.attributeValue("hn");
					String gn = elm.attributeValue("gn");
					sm.setHn(hn.replace("　", ""));
					sm.setGn(gn.replace("　", ""));
					sm.setSort(Integer.valueOf(elm.attributeValue("itemid")));
					sm.setMid(Integer.valueOf(elm.attributeValue("mid")));
					sm.setMatchTime(format.parse(elm.attributeValue("mt")));
					sm.setItemId(Long.valueOf(elm.attributeValue("itemid")));
					sm.setRq(Integer.valueOf(elm.attributeValue("close")));
					sm.setJcName(elm.attributeValue("name"));
					sm.setMname(elm.attributeValue("mname"));//联赛类型
					sm.setRqspf(elm.attributeValue("rqspf"));
					sm.setSpf(elm.attributeValue("spf"));
					sm.setIaudit(elm.attributeValue("iaudit"));
			
					smlist.add(sm);					
				} catch (Exception e) {
					log.error("解析器在解析爬虫数据时异常",e);
				}
			}
		}
	
		return smlist;
	}
	
	private boolean checkResponseData(Object respObj) {
		boolean flag = false;
		if (respObj == null) {
			return flag;
		}
		if (respObj instanceof String) {
			flag = true;
		} else {
			log.error("解析器在解析爬虫数据时，爬虫传入的数据不为String类型");
		}
		return flag;
	}

}
