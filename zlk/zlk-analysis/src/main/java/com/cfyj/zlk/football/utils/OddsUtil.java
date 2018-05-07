package com.cfyj.zlk.football.utils;

import org.apache.commons.lang3.StringUtils;

public class OddsUtil {

	
	/**
	 * 计算欧赔返还率
	 * @param hp  主赔
	 * @param gp  客赔
	 * @param drawp  平赔
	 * @return
	 */
	public static  int calculationFhl(String hp,String gp,String drawp) {
		int fhl = 0;
		if(StringUtils.isNotBlank(hp) && StringUtils.isNotBlank(gp) && StringUtils.isNotBlank(drawp)) {
			fhl = (int) (ArithUtil.div(1,
					(1 + ArithUtil.div(hp, drawp) + ArithUtil.div(hp, gp))) * 100
					* Double.parseDouble(hp));			
		}	
		return fhl;
	}
}
