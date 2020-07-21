package com.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * 自定义异常类 by ChenYb date 2019/4/29
 */
public class MyException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -718639292962381679L;

    /**
     * 错误代码,默认为未知错误
     */
    protected String errorCode = "";

    /**
     * 错误信息中的参数
     */
    protected String[] errorArgs = null;

    /**
     * 兼容纯错误信息，不含error code,errorArgs的情况
     */
    protected String errorMessage = null;

    /**
     * 错误信息的i18n ResourceBundle.
     */
    public MyException() {
        super();
    }

    public MyException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorMessage;
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MyException(ExceptionEnum ecm) {
        this.errorCode = ecm.value();
    }

    /**
     * 获得出错信息. 读取i18N properties文件中Error Code对应的message,并组合参数获得i18n的出错信息.
     */
    @Override
    public String getMessage() {
        // 如果errorMessage不为空,直接返回出错信息.
        if (errorMessage != null) {
            return errorMessage;
        }
        // 否则用errorCode查询Properties文件获得出错信息
        String message = null;
        try {
            // message = rb.getString(String.valueOf(errorCode));
        } catch (MissingResourceException mse) {
            message = "ErrorCode is: " + errorCode + ", but can't get the message of the Error Code";
        }
        // 将出错信息中的参数代入到出错信息中
        if (errorArgs != null) {
            message = MessageFormat.format(message, (Object[]) errorArgs);
        }
        return message;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
