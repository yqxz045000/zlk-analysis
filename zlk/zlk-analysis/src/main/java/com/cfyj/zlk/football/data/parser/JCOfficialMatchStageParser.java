package com.cfyj.zlk.football.data.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.entity.Stage;
import com.cfyj.zlk.football.entity.Stage.StageType;
import com.cfyj.zlk.football.utils.ParseUtil;

/**
 * 解析出竞彩足球的期次
 * @author ls
 * @2018年1月9日
 */
@Component
public class JCOfficialMatchStageParser implements BaseParser<List<Stage>> {
	
	private Logger log = LoggerFactory.getLogger(JCOfficialMatchStageParser.class);

	@Override
	public List<Stage> parse(FetchedPage respObj, Object param) throws Exception {
		List<Stage> stagelist = new ArrayList<Stage>();
		String respStr = "";
		StageType stageType = (StageType) param;
		log.debug("爬虫返回的数据：" + respObj);
		if (ParseUtil.checkResponseDataIsStr(respObj.getContent())) {
			respStr = (String) respObj.getContent();
		} else {
			return stagelist;
		}
		
		Document document = DocumentHelper.parseText(respStr);
		if(document!=null){
			Element root = document.getRootElement();
			Set<String>  set=new HashSet<String>();
			List nodes = root.elements();
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				set.add(elm.attributeValue("itemid").substring(0,6));
			}
			for(String str:set){
				Stage stage = new Stage();
				stage.setName(str);
				stage.setType(stageType.ordinal());
				stagelist.add(stage);
			}
		}
		
		log.debug("输出解析器解析后的数据:" + stagelist);
		return stagelist;
	}

}
