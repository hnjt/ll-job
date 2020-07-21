package com.exception;

/**
 * 异常枚举类 对应数据库scps3.upp_code_item
 * by ChenYb date 2019/4/29
 */
public enum ExceptionEnum {
    EXCEPTION_DATA_CONVERSION("EXCEPTION_DATA_CONVERSION"), //数据转换异常
    EXCEPTION_SUCCESS("EXCEPTION_SUCCESS"), //没有错误
    EXCEPTION_UNKOWN("EXCEPTION_UNKOWN"), //未知异常
    EXCEPTION_USER("EXCEPTION_USER"), //用户名不存在
    EXCEPTION_ROLE("EXCEPTION_ROLE"), //用户没有对应角色
    EXCEPTION_PASSWORD("EXCEPTION_PASSWORD"), //密码错误
    EXCEPTION_AUTH("EXCEPTION_AUTH"), //没有权限
    EXCEPTION_LOGIN("EXCEPTION_LOGIN"), //未登录
    EXCEPTION_CNPC_AD("EXCEPTION_CNPC_AD"), //AD connection error,CNPC账户验证失败，请重试
    EXCEPTION_CNPC_AUTH("EXCEPTION_CNPC_AUTH"), //other error,CNPC账户验证失败，请重试
    EXCEPTION_PARAMETER("EXCEPTION_PARAMETER"), //参数不符合要求
    EXCEPTION_RESTRICTIONS("EXCEPTION_RESTRICTIONS"), //条件限制，操作不允许执行
    EXCEPTION_MAX("EXCEPTION_MAX"), //赋值超出最大值
    EXCEPTION_MIN("EXCEPTION_MIN"), //赋值超出最小值
    EXCEPTION_ID("EXCEPTION_ID"), //唯一标识,不可重复
    EXCEPTION_DATA_NULL("EXCEPTION_DATA_NULL"), //查询不到结果
    EXCEPTION_TRANSACTION("EXCEPTION_TRANSACTION"), //生成/编辑/删除,事务操作失败
    EXCEPTION_ANALYSIS_JSON("EXCEPTION_ANALYSIS_JSON"), //JSON解析失败
    EXCEPTION_ANALYSIS_MXL("EXCEPTION_ANALYSIS_MXL"), //MXL解析失败
    EXCEPTION_ANALYSIS_REPORT("EXCEPTION_ANALYSIS_REPORT"), //解析报告失败
    EXCEPTION_CREATE_TOKEN("EXCEPTION_CREATE_TOKEN"),//token生成失败
    EXCEPTION_ANALYSIS_TOKEN("EXCEPTION_ANALYSIS_TOKEN"),//Token解析失败/失效
    EXCEPTION_TYPE_FILE("EXCEPTION_TYPE_FILE");//文件类型错误
    private String value = "";

    private ExceptionEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static String getEnumValue(String value) {
        for (ExceptionEnum t : ExceptionEnum.values()) {
            if (value.equals(t.toString())) {
                return t.value;
            }
        }
        return null;
    }
}
