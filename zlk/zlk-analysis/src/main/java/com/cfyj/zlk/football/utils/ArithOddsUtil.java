package com.cfyj.zlk.football.utils;

import java.math.BigDecimal;

/**
 * 计算赔率：1.凯利
 * 			2.返还率 
 * @author ls
 * @2018年2月2日
 */
public class ArithOddsUtil {
	
	
	/**
	 * 计算欧赔凯利指数
	 * @return
	 */
	public static double arithKL(String pl,String sl) {
		return 0;
	}
	
	/**
	 * 计算欧赔胜率   公式： 1/(1+主胜赔率/和局赔率 + 主胜赔率/客胜赔率)*100%   参数 1 2 3 分别对应公式里的三个参数
	 * @param odds1  主
	 * @param odds2 平
	 * @param odds3 客
	 * @return
	 */
	public static double arithWinRate(double odds1,double odds2,double odds3) {
		double afgRate= ArithUtil.div(1, 1+ArithUtil.div(odds1, odds2)+ArithUtil.div(odds1, odds3)); 	
		return afgRate;
	}
	
	public static String arithWinRate(String odds1,String odds2,String odds3) {
		double afgRate= ArithUtil.div(1, 1+ArithUtil.div(odds1, odds2)+ArithUtil.div(odds1, odds3),ArithUtil.DIV_SCALE_2,BigDecimal.ROUND_HALF_DOWN); 	
		return afgRate+"";
	}
	
	public static String arithWinRate2(String odds1,String odds2,String odds3) {
		double afgRate= ArithUtil.div(1, 1+ArithUtil.div(odds1, odds2)+ArithUtil.div(odds1, odds3),ArithUtil.DEF_SCALE_6,BigDecimal.ROUND_HALF_DOWN); 	
		return afgRate+"";
	}

}
