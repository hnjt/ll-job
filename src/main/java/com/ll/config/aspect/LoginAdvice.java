package com.ll.config.aspect;//package com.ll.job.config.aspect;
//
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// *  Login&Logout log print By CHENYB date 2019-09-03
// */
//@Order(2)
//@Aspect
//@Component
//public class LoginAdvice{
//
//    /**
//     * 定义一个方法, 用于声明切入点表达式. 一般地, 该方法中再不需要添入其他的代码.
//     * 使用 @Pointcut 来声明切入点表达式.
//     * 后面的其他通知直接使用方法名来引用当前的切入点表达式.
//     * （..）表示任意参数
//     * 这里路径尽量精准，减少资源浪费，或是错过写什么．
//     */
//    @Pointcut("execution(public String com.richfit.user.controller.UserInfoController.*(..))")
//    public void declareJointPointExpression() {
//    }
//
//    @After("declareJointPointExpression()")
//    public void afterMethod(JoinPoint joinPoint) {
//
//        String loginInfoText = "";
//        //获取被调用的方法名
//        String methodName = joinPoint.getSignature().getName();
//
//        /*
//         * 获取签名
//         * 例如 String com.richfit.user.controller.UserInfoController.*(..))
//         * 反射经常应用到 , 这里暂时不需要启用
//         */
//        //Signature signature = joinPoint.getSignature();
//
//        //  获取RequestAttributes
//        RequestAttributes requestAttributes= RequestContextHolder.getRequestAttributes();
//        //  从requestAttributes中获取HttpServletRequest信息 ,如果参数中有, 在参数中提取也可以
//        HttpServletRequest request=(HttpServletRequest)requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//
//        //获取参数
//        Object[] args = joinPoint.getArgs();
//
//        if ("login".equals( methodName )){
//            String loninId = (String) args[1];
//            String clientIp = getClientIp( request );
//            loginInfoText = "系统提示 ######### " + new Date(  ) + "，　帐号："+loninId+"，正在使用："+clientIp+" -> 登录服务器 #########";
//        }
//
//        if ("logOut".equals( methodName )){
//            String userId = (String) args[1];
//            AuthUser authInfo = userService.getAuthInfo( userId );
//            String clientIp = getClientIp( request );
//            loginInfoText = "系统提示 ######### " + new Date(  ) + "，　帐号："+authInfo.getLoginId()+"，正在使用："+clientIp+" -> 已退出登录 #########";
//        }
//
//        logger.info(loginInfoText);
//    }
//
//    //获取用户ip
//    private String getClientIp(HttpServletRequest request) {
//        //X-Forwarded-For，不区分大小写
//        String possibleIpStr = request.getHeader("X-Forwarded-For");
//        String remoteIp = request.getRemoteAddr();
//        String clientIp;
//        if (StringUtils.isNotBlank(possibleIpStr) && !"unknown".equalsIgnoreCase(possibleIpStr)) {
//            //可能经过好几个转发流程，第一个是用户的真实ip，后面的是转发服务器的ip
//            clientIp = possibleIpStr.split(",")[0].trim();
//        } else {
//            //如果转发头ip为空，说明是直接访问的，没有经过转发
//            clientIp = remoteIp;
//        }
//        return clientIp;
//    }
//
//}