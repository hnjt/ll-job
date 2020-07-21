package com.utils;

import java.io.ByteArrayOutputStream;

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
