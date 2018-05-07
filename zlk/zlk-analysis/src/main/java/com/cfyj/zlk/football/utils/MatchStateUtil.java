package com.cfyj.zlk.football.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cfyj.zlk.football.constant.TimeConstant;

/**
 * 比赛状态
 * 
 * @author ls
 * @2018年1月10日
 */
public class MatchStateUtil {

	/*************** iaudit *************/
	public static String inner_jc_iaudit_1 = "1";

	public static String inner_jc_iaudit_0 = "0";

	public static Map<String, String> stateMap = new HashMap<String, String>();

	/******************** inner ***************************/
	// 1:上 ;2：中；3：下；4：完；5：断；8：加；11:点球；13：延期；14:腰斩;“”或 15：待；17：未 ；6取消，改期；; 主站状态
	public final static String INNER_MATCHSTATE_WC = "4";
	public final static String INNER_MATCHSTATE_WKS = "17";

	public final static String INNER_MATCHSTATE_SBC = "1";
	public final static String INNER_MATCHSTATE_ZC = "2";
	public final static String INNER_MATCHSTATE_XBC = "3";
	public final static String INNER_MATCHSTATE_JS = "8";
	public final static String INNER_MATCHSTATE_DQ = "11";

	public final static String INNER_MATCHSTATE_ZD = "5";

	public final static String INNER_MATCHSTATE_TC = "13";
	public final static String INNER_MATCHSTATE_YZ = "14";
	public final static String INNER_MATCHSTATE_DD = "15";
	public final static String INNER_MATCHSTATE_QX = "6";

	/********************** qt ****************************/
	// 0:未开,1:上半场,2:中场,3:下半场,4,加时，-11:待定,-12:腰斩,-13:中断,-14:推迟,-1:完场，-10取消 球探状态

	public final static String QT_MATCHSTATE_WC = "-1";
	public final static String QT_MATCHSTATE_WKS = "0";

	public final static String QT_MATCHSTATE_SBC = "1";
	public final static String QT_MATCHSTATE_ZC = "2";
	public final static String QT_MATCHSTATE_XBC = "3";
	public final static String QT_MATCHSTATE_JS = "4";

	public final static String QT_MATCHSTATE_DD = "-11";
	public final static String QT_MATCHSTATE_YZ = "-12";
	public final static String QT_MATCHSTATE_ZD = "-13";
	public final static String QT_MATCHSTATE_TC = "-14";
	public final static String QT_MATCHSTATE_QX = "-10";

	static {
		stateMap.put(QT_MATCHSTATE_WC, INNER_MATCHSTATE_WC);
		stateMap.put(QT_MATCHSTATE_WKS, INNER_MATCHSTATE_WKS);

		stateMap.put(QT_MATCHSTATE_SBC, INNER_MATCHSTATE_SBC);
		stateMap.put(QT_MATCHSTATE_ZC, INNER_MATCHSTATE_ZC);
		stateMap.put(QT_MATCHSTATE_XBC, INNER_MATCHSTATE_XBC);
		stateMap.put(QT_MATCHSTATE_JS, INNER_MATCHSTATE_JS);
		// INNER_MATCHSTATE_DQ
		stateMap.put(QT_MATCHSTATE_DD, INNER_MATCHSTATE_DD);
		stateMap.put(QT_MATCHSTATE_YZ, INNER_MATCHSTATE_YZ);
		stateMap.put(QT_MATCHSTATE_ZD, INNER_MATCHSTATE_ZD);
		stateMap.put(QT_MATCHSTATE_TC, INNER_MATCHSTATE_TC);
		stateMap.put(QT_MATCHSTATE_QX, INNER_MATCHSTATE_QX);

	}

	/**
	 * 判断比赛是否在进行
	 */
	public static boolean qt_isMatch(String state) {
		boolean flag = false;
		if (StringUtils.isNotBlank(state)) {
			if (QT_MATCHSTATE_SBC.equals(state) || QT_MATCHSTATE_ZC.equals(state) || QT_MATCHSTATE_XBC.equals(state)
					|| QT_MATCHSTATE_JS.equals(state)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 判断比赛是否已完赛（包含已完赛）
	 * 
	 * @return
	 */
	public static boolean qt_isNotMatch(String state) {
		boolean flag = false;
		if (StringUtils.isNotBlank(state)
				&& (QT_MATCHSTATE_DD.equals(state) || QT_MATCHSTATE_YZ.equals(state) || QT_MATCHSTATE_ZD.equals(state)
						|| QT_MATCHSTATE_TC.equals(state) || QT_MATCHSTATE_QX.equals(state))) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断比赛是否已完赛（包含已完赛）
	 * 
	 * @return
	 */
	public static boolean qt_isNotMatch(String state, Date date) {
		boolean flag = false;
		if ((StringUtils.isNotBlank(state) && (QT_MATCHSTATE_WC.equals(state) || QT_MATCHSTATE_DD.equals(state)
				|| QT_MATCHSTATE_YZ.equals(state) || QT_MATCHSTATE_ZD.equals(state) || QT_MATCHSTATE_TC.equals(state)
				|| QT_MATCHSTATE_QX.equals(state))) && System.currentTimeMillis() > date.getTime()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 当前比赛时间为未进行中的比赛，并且当前时间大于比赛开始时间4个小时
	 * 
	 * @param state
	 * @param date
	 * @return
	 */
	public static boolean qt_isNotMatch2(String state, Date date) {
		boolean flag = false;
		if (!qt_isMatch(state) && System.currentTimeMillis() - date.getTime() > TimeConstant.HOUR_4) {
			flag = true;
		}
		return flag;
	}

	public static String getInnerMatchStatus(String state) {
		if (StringUtils.isNotBlank(state)) {
			return stateMap.get(state);
		}
		return " ";
	}

	/**
	 * 爬取 比赛删除和比赛修改数据使用的,有删除和修改两种类型
	 * 
	 * @param str
	 * @return
	 */
	public static String getQTStateByStr(String str) {
		String qtState = null;
		if (StringUtils.isNotBlank(str)) {
			if (str.equals("modify")) {
				return QT_MATCHSTATE_TC;
			} else if (str.equals("delete")) {
				return QT_MATCHSTATE_QX;
			}
		}
		return qtState;

	}

}
