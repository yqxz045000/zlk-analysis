package com.cfyj.zlk.football.analysis.example.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.analysis.example.dao.ExampleDao;
import com.cfyj.zlk.football.analysis.example.domain.Match;
import com.cfyj.zlk.football.entity.Odds;
import com.cfyj.zlk.football.utils.ArithOddsUtil;
import com.cfyj.zlk.football.utils.ArithUtil;
import com.cfyj.zlk.football.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExampleService {

	@Autowired
	private ExampleDao exampleDao; //281-韦德  82-立博  115-威廉希尔
	
	public static final String savePath = "/opt/analysis/";
	
	public static final String saveFileName = "Matchanalysis.xml"; 
	
	public void analysisSPF2() throws Exception{
		List<Match> result  =generatorAnalysisSPF2();
		writeMatchAnalysisXml(result,savePath);
		
		
	}
	
	

	private void writeMatchAnalysisXml(List<Match> result, String savepaht2) {
		File file = new File(savepaht2);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		Document doc = DocumentHelper.createDocument();
		
		Element rootE = doc.addElement("rows");
		rootE.addAttribute("gt", DateUtil.dateToLong2DateStr(new Date()));
		rootE.addAttribute("size",result.size()+"");
		int count =0;	
		
		int bpn = 0; //大概率命中场次
		int lpn = 0;//小概率命中场次
		double bmos = 0;//大概率命中的赔率总和
		double lmos = 0;//小概率命中的赔率总和
		double fhls = 0;//返还率总和
		double winOddsSum =0 ; //总赔率和
		
		
		for(Match match: result) {
			//设置过滤条件
			if(filterMatchAnalsis(match)) {
				continue;
			}else if(match.getBigRate()==1){
				bpn++;
				bmos = bmos +Double.parseDouble(match.getWinOdds());
			}else {
				lpn++;
				lmos = lmos +Double.parseDouble(match.getWinOdds());

			}
			count++;
			Element rowE = rootE.addElement("row");
			winOddsSum = winOddsSum+Double.parseDouble(match.getWinOdds());
			fhls = fhls+ match.getFhl() ;
			
//			rowE.addAttribute("qtid", match.getQt_id()+"");
			rowE.addAttribute("hn", match.getHome_team_name());
			rowE.addAttribute("gn", match.getGuest_team_name());
			rowE.addAttribute("hr", match.getHome_rank());
			rowE.addAttribute("gr", match.getGuest_rank());
			rowE.addAttribute("fsc", match.getFull_score());
			rowE.addAttribute("hsc", match.getHalf_score());//半场比分
			rowE.addAttribute("wH", match.getWinHome());
			rowE.addAttribute("wO", match.getWinOdds());
			rowE.addAttribute("wp", match.getWinProbability()+"");
			rowE.addAttribute("spf", match.getSpf());
			rowE.addAttribute("spfP", match.getSpfProbability());
			rowE.addAttribute("oN", match.getOddsUpdateNum()+"");
			rowE.addAttribute("mt", DateUtil.dateToLong2DateStr(match.getMatch_time()));
		}
		rootE.addAttribute("filterNum", count+"");
		rootE.addAttribute("filterRate", ArithUtil.div(count+"",result.size()+"")*100+"%"); //过滤数据占比
		rootE.addAttribute("avgwinOdds", winOddsSum/count +"");
		rootE.addAttribute("avg_fhl", fhls/count +"");
		rootE.addAttribute("bpn_r", ArithUtil.div(bpn+"",count+"")*100+"%"); //大概率命中占比
		rootE.addAttribute("avg_bmo", bmos/bpn +"");
		rootE.addAttribute("lpn_r", ArithUtil.div(lpn+"",count+"")*100+"%"); //小概率命中占比
		rootE.addAttribute("avg_lmo", lmos/lpn +"");

		try {
		
//			doc.write(new FileWriter(savepaht2+saveFileName));
			
	        OutputFormat xmlFormat = new OutputFormat();  
	        xmlFormat.setEncoding("UTF-8"); 
	        // 设置换行 
	        xmlFormat.setNewlines(true); 
	        // 生成缩进 
	        xmlFormat.setIndent(true); 
	        // 使用4个空格进行缩进, 可以兼容文本编辑器 
	        xmlFormat.setIndent("    ");         
	        //创建写文件方法  
	        XMLWriter xmlWriter = new XMLWriter(new FileWriter(savepaht2+saveFileName),xmlFormat);  
	        //写入文件  
	        xmlWriter.write(doc);  
	        //关闭  
	        xmlWriter.close(); 
		} catch (IOException e) {
			log.error("生成比赛分析文件异常",e);
		}
		
	}


	public boolean filterMatchAnalsis(Match match) {
		boolean flag = true;
		String spfProbability = match.getSpfProbability();
		String[] split = spfProbability.split(",");
		for(String str:split) {
			if(Double.parseDouble(str)>50 ) {//&& match.getOddsUpdateNum()>14
				if(match.getWinProbability().toString().equals(str)) {
					match.setBigRate(1);
				}
				flag = false;
				return flag;
			}		
		}

		return flag ;
	}
	
	
	
	

	public List<Match> generatorAnalysisSPF2() throws Exception {

		/**
		 * 1.根据条件查比赛
		 * 2.过滤掉没有赔率的比赛
		 * 3.解析赔率数据，然后生成新的比赛结果
		 */
		List<Match> list = exampleDao.findCondition();
		List<Match> list2 = new ArrayList<>(40000);
		if(list==null || list.size()==0) {
			log.info("未根据条件查找到相关比赛信息，不生成比赛分析文件");
			return list2;
		}
		
		for(Match match: list) {
			Odds odds = exampleDao.findOdds2OneByQtid(match.getQt_id(),"82");
			if(odds!=null ) {
				match.setOdds(odds);
				list2.add(match);
			}	
		}

		log.info("根据条件获取到:" +list.size()+",其中有赔率比赛场次为："+list2.size());
		ListIterator<Match> listIterator = list2.listIterator();
		int errorNum = 0;
		while(listIterator.hasNext()) {
			
			Match match = listIterator.next();
			try {
				matchAnalylsis(match);
				
			} catch (Exception e) {
				errorNum++;
				log.error("分析比赛出现异常,比赛数据为：{}",match,e);
				listIterator.remove();			
			}
		}
		log.info("生成比赛分析的长度为：{},异常数据size：{}",list2.size(),errorNum);
			
		return list2;
			
	}

	
	
	public void matchAnalylsis(Match match) {
		
		if(match!=null && match.getQt_id()!=null ) {
			Odds odds = match.getOdds();
			int flag = 0;
			String[] matchResult = { "主胜", "平局", "客胜" };
			String oddsA[] = odds.getNewOdds().split(",");
			String[] split = match.getFull_score().split("-");
			int hsc = Integer.parseInt(split[0]);
			int gsc = Integer.parseInt(split[1]);
			Double oddsrate[] = getOddsRate(oddsA);
			
			if (hsc > gsc) {
				flag = 0;
			} else if (hsc == gsc) {
				flag = 1;
			} else {
				flag = 2;
			}
			
			match.setWinHome(matchResult[flag]);
			match.setWinOdds(oddsA[flag]);
			match.setSpf(odds.getNewOdds());
			match.setOddsUpdateNum(odds.getAllOdds().split(";").length);
			match.setWinProbability(oddsrate[flag]);
			match.setSpfProbability(oddsrate[0]+","+oddsrate[1]+","+oddsrate[2]+",");
			match.setFhl(Double.parseDouble(oddsA[3]));
		}
	}
	

	/**
	 * 计算各赔率的胜率
	 * 
	 * @return
	 */
	public Double[] getOddsRate(String odds[]) {

		Double result[] = new Double[3];

		result[0] =  ArithUtil.mul(ArithOddsUtil.arithWinRate(odds[0], odds[1], odds[2]), "100") ;
		result[1] =  ArithUtil.mul(ArithOddsUtil.arithWinRate(odds[1], odds[0], odds[2]), "100") ;
		result[2] =  ArithUtil.mul(ArithOddsUtil.arithWinRate(odds[2], odds[0], odds[1]), "100");

		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public void analysisSPF() throws Exception {
//
//		List<Match> list = exampleDao.findCondition();
//		List<Match> data = new ArrayList(40000);
//		List<MatchResult> cgData = new ArrayList(40000);
//		for (Match match : list) {
//
//			OddsHundred odds = exampleDao.findOddsOneByQtid(match.getQt_id());
//			if (odds != null && StringUtils.isNoneBlank(odds.getOdds_new())) {
//				match.setSpf(odds.getOdds_new());
//				data.add(match);
//			}
//
//		}
//
//		log.info("查询match：{},拥有赔率match：{}", list.size(), data.size());
//		for (Match match : data) {
//			try {
//				if (StringUtils.isBlank(match.getFull_score()) || match.getFull_score().indexOf("-") == -1) {
//					log.info("没有比赛结果，跳过数据，match:{}", match);
//					continue;
//				}
//				MatchResult result = getCG(match);
//				cgData.add(result);
//			} catch (Exception e) {
//				log.error("获取彩果异常,match:{}", match, e);
//			}
//		}
//
//		log.info("查询match：{},拥有赔率match：{},计算出彩果的match:{}", list.size(), data.size(), cgData.size());
//
//		JSON.writeJSONString(new FileOutputStream("F:\\cg.txt"), cgData, SerializerFeature.WriteNullStringAsEmpty);
//
//	}
//
//	private MatchResult getCG(Match match) {
//		MatchResult result = null;
//		if (match.getSpf().indexOf("p3") > -1) {
//			result = new MatchResult();
//			result.setHomeName(match.getHome_team_name());
//			result.setGuestName(match.getGuest_team_name());
//			JSONArray jb = JSON.parseArray(match.getSpf());
//			String[] resultcg = getMatchResult(match.getFull_score(), jb);
//			result.setCg(resultcg[0]);
//			result.setCgOdds(resultcg[1]);
//			result.setSuccessRate(resultcg[2]);
//			result.setQtid(match.getQt_id());
//			result.setSpf("");
//			result.setMatchTime(match.getMatch_time());
//		}
//
//		return result;
//	}
//
//	/**
//	 * 获取比赛结果
//	 * 
//	 * @return
//	 */
//	public String[] getMatchResult(String fullScore, JSONArray jb) {
//		String[] result = { "", "", "" };
//		String[] matchResult = { "主胜", "平局", "客胜" };
//
//		String odds[] = new String[3];
//		odds[0] = jb.getJSONObject(0).getString("p1");
//		odds[1] = jb.getJSONObject(0).getString("p2");
//		odds[2] = jb.getJSONObject(0).getString("p3");
//		String oddsrate[] = getSuccessRate(odds);
//		if (StringUtils.isNoneBlank(fullScore) && fullScore.contains("-")) {
//
//			String[] split = fullScore.split("-");
//			int hsc = Integer.parseInt(split[0]);
//			int gsc = Integer.parseInt(split[1]);
//			int flag = 0;
//
//			String cg = "";
//			if (hsc > gsc) {
//				flag = 0;
//			} else if (hsc == gsc) {
//				flag = 1;
//			} else {
//				flag = 2;
//			}
//			result[0] = matchResult[flag];
//			result[1] = odds[flag];
//			result[2] = oddsrate[flag];
//		}
//		return result;
//	}
//
//	/**
//	 * 计算胜率
//	 * 
//	 * @return
//	 */
//	public String[] getSuccessRate(String odds[]) {
//
//		String result[] = new String[3];
//
//		result[0] = (int) ArithUtil.mul(ArithOddsUtil.arithWinRate(odds[1], odds[0], odds[2]), "100") + "%";
//		result[1] = (int) ArithUtil.mul(ArithOddsUtil.arithWinRate(odds[0], odds[1], odds[2]), "100") + "%";
//		result[2] = (int) ArithUtil.mul(ArithOddsUtil.arithWinRate(odds[2], odds[1], odds[0]), "100") + "%";
//
//		return result;
//	}

}
