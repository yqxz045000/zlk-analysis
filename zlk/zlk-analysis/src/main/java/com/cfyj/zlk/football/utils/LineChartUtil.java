package com.cfyj.zlk.football.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.Record;

import com.cfyj.zlk.football.domain.MatchOddsResultExportDomain;
import com.cfyj.zlk.football.entity.EchartsEntity;
import com.cfyj.zlk.football.entity.Match;

public class LineChartUtil {
	
	
	
	
	
	
	public static void main(String[] args) {

			
			
			
			
			
			
			
	}
	
	
	public static Map<String,Object> pointUselessAnalyze(List<MatchOddsResultExportDomain> datas){  

		
		List<String> legend = new ArrayList<>();//放置折线图每条信息的颜色  
		List<Double> echarts1 = new ArrayList<>();//折线1  
		List<Double> echarts2 = new ArrayList<>();//折线2  
		List<Double> echarts3 = new ArrayList<>();//折线3  
		List<EchartsEntity> echarts = new ArrayList<>();//EchartsEntity对象中 三个属性：String,String,List -> 折线注释，折线typ
			
		String[] xAxis = null;//x轴  
           
 
		if (datas != null && datas.size() > 0) {  
		    xAxis = new String[datas.size()];//x轴的长度  
		    for(int i = 0; i < datas.size();i++){//循环mysql结果  
		    xAxis[i] = datas.get(i).getUpdateTime();//我是以时间作为x轴  
		    echarts1.add(datas.get(i).getWinOddsRate());//折线1  
		    echarts2.add(datas.get(i).getDrawOddsRate());//折线2  
		    echarts3.add(datas.get(i).getFailOddsRate());//折线3  
		    }  
		}  
		          		
//		EchartsEntity entity1 = new EchartsEntity("s","<span style=\"background-color:#436EEE;\">line</span>",echarts1);
//		EchartsEntity entity2 = new EchartsEntity("p","<span style=\"background-color:#EEB422;\">line</span>",echarts2);  
//		EchartsEntity entity3 = new EchartsEntity("f","<span style=\"background-color:#32CD32;\">line</span>",echarts3);  
		EchartsEntity entity1 = new EchartsEntity("s","line",echarts1);
		EchartsEntity entity2 = new EchartsEntity("p","line",echarts2);  
		EchartsEntity entity3 = new EchartsEntity("f","line",echarts3);  
		echarts.add(entity1);  
		echarts.add(entity2);  
		echarts.add(entity3);  
		legend.add("s");  
		legend.add("p");  
		legend.add("f");  
		Map<String,Object> ret=new HashMap<>();  
		ret.put("xAxis", xAxis);//x轴信息  
		ret.put("series", echarts);//折线内容  
		ret.put("legend", legend);//折线颜色和折线颜色所代表的的信息  
		return ret;//返回结果Map<String,Object>  
		}  
	
	

}
