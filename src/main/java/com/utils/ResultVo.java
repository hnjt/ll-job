/**
 * 文件名：ResultVo.java
 * 
 *北京中油瑞飞信息技术有限责任公司(http://www.richfit.com)
 * Copyright © 2017 Richfit Information Technology Co., LTD. All Right Reserved.
 */
package com.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <p>
 * <li>Description:</li>
 * <li>$Author$</li>
 * <li>$Revision: 4547 $</li>
 * <li>$Date: 2017-05-12 16:10:40 +0800 (Fri, 12 May 2017) $</li>
 * 
 * @version 1.0
 */
public class ResultVo {

    /**
     * 是否成功
     */
    private boolean success = true;

    /**
     * 需要返回到前台的数据
     */
    private Object data;

    /**
     * 消息
     */
    private String msg = "成功";

    /**
     * 错误编码
     */
    private String errorCode = "";

    public ResultVo(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ResultVo(Object data) {
        this.data = data;
    }

    public ResultVo() {
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String toJSONString() {
        JSONObject obj = new JSONObject();
        if (this.isSuccess()) {
            obj.put("data", getData() == null ? "" : getData());
            obj.put("success", true);
        } else {
            JSONObject errorObj = new JSONObject();
            errorObj.put("code", getErrorCode());
            errorObj.put("message", getMsg());
            obj.put("error", errorObj);
            obj.put("success", false);
        }
        return JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }
    public String toJSONStringWithOutNull() {
        JSONObject obj = new JSONObject();
        if (this.isSuccess()) {
            obj.put("data", getData() == null ? "" : getData());
            obj.put("success", true);
        } else {
            JSONObject errorObj = new JSONObject();
            errorObj.put("code", getErrorCode());
            errorObj.put("message", getMsg());
            obj.put("error", errorObj);
            obj.put("success", false);
        }
        return JSONObject.toJSONString(obj);
    }

    /**
     * 获得success
     *
     * @return success success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设定success
     *
     * @param success success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获得msg
     *
     * @return msg msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设定msg
     *
     * @param msg msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获得data
     *
     * @return data data
     */
    public Object getData() {
        return data;
    }

    /**
     * 设定data
     *
     * @param data data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获得errorCode
     *
     * @return errorCode errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设定errorCode
     *
     * @param errorCode errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
