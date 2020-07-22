package com.ll.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Java 时间与Cron 转换工具类 by CHENYB date 2019-07-30
 */
public class DateCommutativeCronUtil {

    private static final String DATEFORMAT = "ss mm HH dd MM ? yyyy";

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * @param cron
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String cron, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        if (cron != null) {
            date = sdf.parse(cron);
        }
        return date;
    }

    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date, DATEFORMAT);
    }

    /***
     * convert cron to Date
     * @param cron  : cron表达式 cron表达式仅限于周为*
     * @return
     */
    public static Date getDate(String cron) throws ParseException {
        return parseStringToDate(cron, DATEFORMAT);
    }

    private static String getDateFormat (String dateFormat){
        if (StringUtils.isBlank( dateFormat ))
            return DATEFORMAT;
        return dateFormat;
    }


}
