/**
 * 文件名：DateStringCalcUtils.java
 * 
 *北京中油瑞飞信息技术有限责任公司(http://www.richfit.com)
 * Copyright © 2017 Richfit Information Technology Co., LTD. All Right Reserved.
 */
package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateStringCalcUtils {

    /**
     * 日期格式化字符串：yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 日期格式化字符串：yyyyMMddHHmm
     */
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

    /**
     * 日期格式化字符串：yyyyMMddHH
     */
    public static final String YYYYMMDDHH = "yyyyMMddHH";

    /**
     * 日期格式化字符串：yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式化字符串：yyyyMM
     */
    public static final String YYYYMM = "yyyyMM";

    /**
     * 日期格式化字符串：yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化字符串：yyyy-MM-dd HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式化字符串：yyyy-MM-dd HH
     */
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

    /**
     * 日期格式化字符串：yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日志对象
     */
    private static Log log = LogFactory.getLog(DateStringCalcUtils.class);

    /**
     * 将指定的日期转换为一般正常使用的字符串
     * 
     * @param date 日期类型变量
     * @return 一般正常使用的字符串（年-月-日）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：yyyy-MM-dd如"2012-08-22"</li>
     */
    public static String dateToStringToGeneralYMD(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYY_MM_DD);
    }

    /**
     * 将指定的日期转换为一般正常使用的字符串
     * 
     * @param date 日期类型变量
     * @return 一般正常使用的字符串（年-月-日 小时）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：yyyy-MM-dd HH如"2012-08-22 18"</li>
     */
    public static String dateToStringToGeneralYMDH(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYY_MM_DD_HH);
    }

    /**
     * 将指定的日期转换为一般正常使用的字符串
     * 
     * @param date 日期类型变量
     * @return 一般正常使用的字符串（年-月-日 小时:分）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：yyyy-MM-dd HH:mm如"2012-08-22 18:01"</li>
     */
    public static String dateToStringToGeneralYMDHM(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYY_MM_DD_HH_MM);
    }

    /**
     * 将指定的日期转换为一般正常使用的字符串
     * 
     * @param date 日期类型变量
     * @return 一般正常使用的字符串（年-月-日 小时:分:秒）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：yyyy-MM-dd HH:mm:ss如"2012-08-22 18:01:01"</li>
     */
    public static String dateToStringToGeneralYMDHMS(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将指定的日期转换为6位长度的纯数字字符串
     * 
     * @param date 日期类型变量
     * @return 6位长度的纯数字字符串（年月）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：YYYYMM如"201208"</li>
     */
    public static String dateToStringTo06LengthNumber(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYYMM);
    }

    /**
     * 将指定的日期转换为8位长度的纯数字字符串
     * 
     * @param date 日期类型变量
     * @return 8位长度的纯数字字符串（年月日）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：YYYYMMDD如"20120822"</li>
     */
    public static String dateToStringTo08LengthNumber(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYYMMDD);
    }

    /**
     * 将指定的日期转换为10位长度的纯数字字符串
     * 
     * @param date 日期类型变量
     * @return 10位长度的纯数字字符串（年月日小时）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：YYYYMMDDHH如"2012082218"</li>
     */
    public static String dateToStringTo10LengthNumber(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYYMMDDHH);
    }

    /**
     * 将指定的日期转换为12位长度的纯数字字符串
     * 
     * @param date 日期类型变量
     * @return 12位长度的纯数字字符串（年月日小时分）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：YYYYMMDDHHMM如"201208221801"</li>
     */
    public static String dateToStringTo12LengthNumber(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYYMMDDHHMM);
    }

    /**
     * 将指定的日期转换为14位长度的纯数字字符串
     * 
     * @param date 日期类型变量
     * @return 14位长度的纯数字字符串（年月日小时分秒）
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null则返回空""字符串</li>
     *         <li>正常返回：YYYYMMDDHHMMSS如"20120822180101"</li>
     */
    public static String dateToStringTo14LengthNumber(Date date) {
        if (null == date) {
            log.debug("日期类型参数为null，方法返回''空字符串！");
            return "";
        }
        return formatDate(date, YYYYMMDDHHMMSS);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 8位长度的纯数字日期格式字符串（年月日）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为YYYYMMDD如"20120822"则返回Date对象</li>
     */
    public static Date stringToDateBy08LengthNumber(String source) {
        return parseToDate(source, YYYYMMDD);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 10位长度的纯数字日期格式字符串（年月日小时）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为YYYYMMDDHH如"2012082218"则返回Date对象</li>
     */
    public static Date stringToDateBy10LengthNumber(String source) {
        return parseToDate(source, YYYYMMDDHH);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 12位长度的纯数字日期格式字符串（年月日小时分）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为YYYYMMDDHHMM如"201208221801"则返回Date对象</li>
     */
    public static Date stringToDateBy12LengthNumber(String source) {
        return parseToDate(source, YYYYMMDDHHMM);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 14位长度的纯数字日期格式字符串（年月日小时分秒）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为YYYYMMDDHHMMSS如"20120822180101"则返回Date对象</li>
     */
    public static Date stringToDateBy14LengthNumber(String source) {
        return parseToDate(source, YYYYMMDDHHMMSS);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 一般正常使用的日期格式字符串（年-月-日）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为yyyy-MM-dd如"2012-08-22"则返回Date对象</li>
     */
    public static Date stringToDateByGeneralYMD(String source) {
        return parseToDate(source, YYYY_MM_DD);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 一般正常使用的日期格式字符串（年-月-日 小时）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为yyyy-MM-dd HH如"2012-08-22 18"则返回Date对象</li>
     */
    public static Date stringToDateByGeneralYMDH(String source) {
        return parseToDate(source, YYYY_MM_DD_HH);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 一般正常使用的日期格式字符串（年-月-日 小时:分）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为yyyy-MM-dd HH:mm如"2012-08-22 18:01"则返回Date对象</li>
     */
    public static Date stringToDateByGeneralYMDHM(String source) {
        return parseToDate(source, YYYY_MM_DD_HH_MM);
    }

    /**
     * 将指定的日期格式字符串转换为日期类型
     * 
     * @param source 一般正常使用的日期格式字符串（年-月-日 小时:分:秒）
     * @return 日期类型
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空null或者正常值）</li>
     *         <li>异常返回：如果source为null或非法格式字符串则返回null</li>
     *         <li>正常返回：source为yyyy-MM-dd HH:mm:ss如"2012-08-22 18:01:01"则返回Date对象</li>
     */
    public static Date stringToDateByGeneralYMDHMS(String source) {
        return parseToDate(source, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将指定的日期根据指定的模式转换为相应字符串
     * 
     * @param date 日期类型变量
     * @param pattern 描述日期和时间格式的模式
     * @return 已格式化的时间字符串
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为空""字符串或者正常值）</li>
     *         <li>异常返回：如果date为null或者pattern为null则返回空""字符串</li>
     *         <li>正常返回：已格式化的时间字符串</li>
     */
    private static String formatDate(Date date, String pattern) {
        // 返回值
        String rstString = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            rstString = sdf.format(date);
        } catch (NullPointerException npe) {
            log.error(npe.getStackTrace());
            log.error("日期为null或者模式字符串为null！");
        } catch (IllegalArgumentException iae) {
            log.error(iae.getStackTrace());
            log.error("非法模式字符串！");
        }
        return rstString;
    }

    /**
     * 获取Long类型的时间戳
     * 
     * @return
     * @author zhaoyf
     * @create 下午2:11:16
     */
    public static Long getLongTimestamp() {
        return new Date().getTime();
    }

    /**
     * 获取String类型的时间戳
     * 
     * @return
     * @author zhaoyf
     * @create 下午2:11:16
     */
    public static String getStringTimestamp() {
        return getLongTimestamp().toString();
    }

    /**
     * 将指定的字符串转换为日期类型
     * 
     * @param source 要转换成日期的字符串
     * @param pattern 描述日期和时间格式的模式
     * @return 转换后的日期
     *         <p>
     *         <li>调用此方法不会出现任何程序上的错误（返回值永远为null或者正常值）</li>
     *         <li>异常返回：null</li>
     *         <li>正常返回：转换后的日期/li>
     */
    private static Date parseToDate(String source, String pattern) {
        // 返回值
        Date rstDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            rstDate = sdf.parse(source);
        } catch (NullPointerException npe) {
            log.error(npe.getStackTrace());
            log.error("要转换成日期的字符串为null或者模式字符串为null！");
        } catch (IllegalArgumentException iae) {
            log.error(iae.getStackTrace());
            log.error("非法模式字符串！");
        } catch (ParseException pe) {
            log.error(pe.getStackTrace());
            log.error("无法解析指定字符串！");
        }
        return rstDate;
    }
}
