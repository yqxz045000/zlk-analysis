package com.cfyj.zlk.football.analysis.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cfyj.zlk.football.analysis.task.ExportMatchOddsResultTask;
import com.cfyj.zlk.football.data.dao.MatchOddsResultMapper;
import com.cfyj.zlk.football.domain.MatchOddsResultExportDomain;
import com.cfyj.zlk.football.entity.MatchOddsResult;
import com.cfyj.zlk.football.utils.ArithOddsUtil;
import com.cfyj.zlk.football.utils.ArithUtil;
import com.cfyj.zlk.football.utils.DateUtil;
import com.cfyj.zlk.football.utils.ExcelUtil;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExportAnalysisData {
	
	@Autowired
	private MatchOddsResultMapper matchOddsResultMapper;
	
	private static final String SAVEPATH = "E:\\matchAnalysis\\";

	
	@Autowired
	ExportMatchOddsResultTask exportMatchOddsResultTask;
	
	@Test
	public void export2() {
		exportMatchOddsResultTask.task();
	}
	
	@Test
	public void export() throws FileNotFoundException, Exception {
		long qtid = 1521060;
		List<MatchOddsResult> list  = matchOddsResultMapper.findByQtid(qtid);
//		String[] colNames = { "win", "draw", "fail", "fhl", "updateTime"};
		String[] colNames = { "1", "2", "3", "4", "5"};
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();  
		map.put("1", "winOddsRate");  
		map.put("2", "drawOddsRate");  
		map.put("3", "failOddsRate");  
		map.put("4", "fhl");  
		map.put("5", "updateTime");  
  
//		String[] includeFieldNames = { "winOddsRate", "drawOddsRate", "failOddsRate","fhl","updateTime"};
		List<MatchOddsResultExportDomain> datas = null;
		if(list!=null) {
			for(ListIterator<MatchOddsResult> it = list.listIterator();it.hasNext();) {
				MatchOddsResult mdr = it.next();
				String[] oddsAllA = mdr.getAllOdds().split(";");
				datas = new ArrayList<>();
				for(int i =0;i<oddsAllA.length;i++) {
					if(StringUtils.isNotBlank(oddsAllA[i])) {
						MatchOddsResultExportDomain me = new MatchOddsResultExportDomain();
						String oddsStr [] = oddsAllA[i].split(",") ;
						Double[] rateA  = getOddsRate(oddsStr);
						if(rateA.length<3) {
							continue;
						}
						me.setWinOddsRate(rateA[0]);
						me.setDrawOddsRate(rateA[1]);
						me.setFailOddsRate(rateA[2]);
						me.setFhl(oddsStr[3]);
						me.setUpdateTime(oddsStr[4]);

						datas.add(me);
					}
				}
				
				if(datas!=null && datas.size()>0) {
					String path =SAVEPATH+"\\"+mdr.getQtid()+"_"+mdr.getHn()+"vs"+mdr.getGn()+"_"+DateUtil.dateToLong4SdfDate(mdr.getMatchTime())+"\\" ;
					File dir = new File(path);
					if(!dir.exists()) {
						dir.mkdirs();
					}
					String fileName = "filename";
					try {
						ExcelUtil.downloadExcel(fileName, colNames, map, "无",  
								datas, new FileOutputStream(path+mdr.getCompanyId()+".xls"));  						
					} catch (Exception e) {
						log.error("导出比赛赔率结果异常",e);
					}
		           
				}
				
			}
			
			
		}
		
		
	}
	
	
	/**
	 * 计算各赔率的胜率
	 * 
	 * @return
	 */
	private Double[] getOddsRate(String odds[]) {

		Double result[] = new Double[3];

		result[0] = ArithUtil.mul(ArithOddsUtil.arithWinRate2(odds[0], odds[1], odds[2]), "100"); // 主胜
		result[1] = ArithUtil.mul(ArithOddsUtil.arithWinRate2(odds[1], odds[0], odds[2]), "100");// 平
		result[2] = ArithUtil.mul(ArithOddsUtil.arithWinRate2(odds[2], odds[0], odds[1]), "100");// 客胜

		return result;
	}}
