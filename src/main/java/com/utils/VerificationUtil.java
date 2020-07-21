package com.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class VerificationUtil {

	//正则规则
	public final static String regex = "'|#|%|;|--| and | and|and | or | or|or | not | not|not " +
			"| use | use|use | insert | insert|insert | delete | delete|delete | update | update|update " +
			"| select | select|select | count | count|count | group | group|group | union | union|union " +
			"| create | create|create | drop | drop|drop | truncate | truncate|truncate | alter | alter|alter " +
			"| grant | grant|grant | execute | execute|execute | exec | exec|exec | xp_cmdshell | xp_cmdshell|xp_cmdshell " +
			"| call | call|call | declare | declare|declare | source | source|source | sql | sql|sql ";

	public static boolean isValidated(String originStr,int DBLength,String verifyRange,boolean notEmpty){
		int config = 0;
		/*是否允许为空 ture 不允许为空*/
		if (StringUtils.isEmpty(originStr)|| StringUtils.isBlank(originStr))
			if (!notEmpty)
				return true;
			else
				return false;

		/*sql安全值,占位*会报错,这里不限制*/
		String newOriginStr = originStr.replaceAll("(?i)" + regex, "");
		log.debug("analysis before {}",originStr);
		log.debug("analysis later {}",newOriginStr);
		if (newOriginStr.length() < originStr.length())
			config += 1;
		if (originStr.contains( "{" )
				&&originStr.contains( "}" )
				&&originStr.contains( ":" )
				&&(originStr.contains( "'" )||originStr.contains( "\"" ))
		){
			log.debug("analysis JSON ：true --- Length check skip");
			config = 0;
		}


		/*预订长度范围*/
		if (0 != DBLength)
			if (newOriginStr.length() > DBLength)
				config += 1;
		log.debug("analysis valid length {}",DBLength);
		log.debug("analysis origin length {}",newOriginStr.length());
		/*预定值范围*/
		if (null != verifyRange && StringUtils.isNotEmpty(verifyRange.trim())) {
			if (!verifyRange.contains(originStr)) {
				config += 1;
			}
			log.debug("VERIFY RANGE   ==>{}<==",verifyRange);
		}else
			log.debug("VERIFY RANGE   ==>{}<==","NOT RANGE!");
		log.info("isVaild - config :{}",config);
		return config==0?true:false;
	}

	/*-------------------------2019-05-14 by ChenYb iteration ------end------ -------------*/

	/**
	 * 验证数据原值类型
	 * 返回 String 对象类型
	 * @param obj
	 * @return
	 * by ChenYb date 2019-05-16
	 */
	public static String verifyType(Object obj){
		String result = "";
		if (null == obj){
			result = "null";
		} else if (obj instanceof Integer) {
			int i = ((Integer) obj).intValue();
			result = "Integer";
		}else if (obj instanceof String) {
			String s = (String) obj;
			result = "String";
		} else if (obj instanceof Double) {
			double d = ((Double) obj).doubleValue();
			result = "Double";
		} else if (obj instanceof Float) {
			float f = ((Float) obj).floatValue();
			result = "Float";
		} else if (obj instanceof Long) {
			long l = ((Long) obj).longValue();
			result = "Long";
		} else if (obj instanceof Boolean) {
			boolean b = ((Boolean) obj).booleanValue();
			result = "Boolean";
		} else if (obj instanceof Date) {
			Date d = (Date) obj;
			result = "Date";
		}
		return result;
	}

	/**
	 * 验证数据能否成功转型
	 * obj 原值 ; type 类型,不区分大小写,非Date类型,可以简写,比如,Integer : int String : s
	 * @param obj
	 * @param type
	 * @return
	 * by ChenYb date 2019-05-16
	 */
	public static boolean verifyType(Object obj,String type){
		boolean config = false;

		if (null == obj || StringUtils.isEmpty(type)|| StringUtils.isBlank(type))
			return config;

		try {
			if ("INTEGER".contains(type.toUpperCase()))
				Integer.valueOf(String.valueOf(obj));

			else if ("STRING".contains(type.toUpperCase())){

				if (obj instanceof Date)
					new SimpleDateFormat("yyyy-MM-dd").format((Date) obj);
				else
					String.valueOf(obj);
			}
			else if ("DOUBLE".contains(type.toUpperCase()))
				Double.valueOf(String.valueOf(obj));

			else if ("FLOAT".contains(type.toUpperCase()))
				Float.valueOf(String.valueOf(obj));

			else if ("LONG".contains(type.toUpperCase()))
				if (obj instanceof Date)
					((Date) obj).getTime();
				else
					Long.valueOf(String.valueOf(obj));

			else if ("BOOLEAN".contains(type.toUpperCase()))
				Boolean.valueOf(String.valueOf(obj));

			else if ("DATE".equals(type.toUpperCase())) {
				if (obj instanceof Long)
					new Date(((Long) obj).longValue());
				else
					new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(obj));
			}

			config = true;
		}catch (Exception e){
			log.debug("obj : {},不允许转型 {}",obj,type.toUpperCase());
		}
		return config;
	}

}
