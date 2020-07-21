/**
 * 文件名：LogUtils.java
 * 
 * 北京中油瑞飞信息技术有限责任公司(http://www.richfit.com)
 * Copyright © 2010 北京中油瑞飞信息技术有限责任公司 All Right Reserved.
 */
package com.utils;

import java.io.ByteArrayOutputStream;

/**
 * <p>
 * <li>Description:</li>
 * <li>$Author$</li>
 * <li>$Revision: 4108 $</li>
 * <li>$Date: 2017-03-06 09:38:17 +0800 (Mon, 06 Mar 2017) $</li>
 * 
 * @version 1.0
 */
public class LogUtils {

    /**
     * @param e
     * @return
     * @author zhaoyf
     * @create 下午1:56:26
     */
    public static String getErrorMsg(Exception e) {
        String msg = null;
        try {
            ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
            e.printStackTrace(new java.io.PrintWriter(buf, true));
            msg = buf.toString();
            buf.close();
        } catch (Exception e2) {
            msg = e.getMessage();
        }
        return msg;
    }
}
