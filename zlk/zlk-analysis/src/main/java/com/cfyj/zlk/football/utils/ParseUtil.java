package com.cfyj.zlk.football.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 解析工具类
 * @author ls
 * @2018年1月9日
 */

public class ParseUtil {
	private static Logger log = LoggerFactory.getLogger(ParseUtil.class);
	
	/**
	 * 检测数据是否为String类型，是则返回true，不是或为null则返回false
	 * @param respObj
	 * @return
	 */
	public static boolean checkResponseDataIsStr(Object respObj) {
		boolean flag = false;
		if (respObj == null) {
			return flag;
		}
		if (respObj instanceof String) {
			flag = true;
		} else {
			log.error("非string类型");
		}
		return flag;
	}

}
