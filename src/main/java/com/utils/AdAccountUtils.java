/**
 * 文件名：AdAccountUtils.java
 * 
 *北京中油瑞飞信息技术有限责任公司(http://www.richfit.com)
 * Copyright © 2017 Richfit Information Technology Co., LTD. All Right Reserved.
 */
package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * <li>Description:</li>
 * <li>$Author$</li>
 * <li>$Revision: 6313 $</li>
 * <li>$Date: 2018-06-07 17:21:32 +0800 (Thu, 07 Jun 2018) $</li>
 * 
 * @version 1.0
 */
public class AdAccountUtils {

    protected static Log log = LogFactory.getLog(AdAccountUtils.class);

    /**
     * ptr邮箱
     */
    private static String ACOUNT_PTR_SUFFIX = "@petrochina.com.cn";

    /**
     * cnpc邮箱
     */
    private static String ACOUNT_CNPC_SUFFIX = "@cnpc.com.cn";

    /**
     * AD账号登录
     * 
     * @param email
     * @param password
     * @return
     */
    public static int adLogin(String email, String password){
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return -1;
        }
        if (email.endsWith(ACOUNT_PTR_SUFFIX)) {
            return ptrLogin(email, password);
        } else if (email.endsWith(ACOUNT_CNPC_SUFFIX)) {
            return cnpcLogin(email, password);
        }
        return -1;
    }

    /**
     * @param email
     * @param password
     * @return
     */
    private static int cnpcLogin(String email, String password){
        //return login("cnpc.com.cn", 389, email, password);
        return CNPCUserValid(email.replace("@cnpc.com.cn", ""),password);
    }

    /**
     * @param email
     * @param password
     * @return
     */
    private static int ptrLogin(String email, String password){
        return login("ptr.petrochina", 389, email, password);
    }

    /**
     * 使用AD域用户鉴权
     * 
     * @param host 主机名
     * @param post 端口
     * @param username 登陆账户
     * @param password 登陆密码
     * @return true:登录成功 false 登录失败
     * @author zhaoyf
     */
    private static int login(String host, int post, String username, String password){
        DirContext ctx = null;
        Hashtable<String, String> HashEnv = new Hashtable<String, String>();
        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别(none,simple,strong)
        HashEnv.put(Context.SECURITY_PRINCIPAL, username); // AD的用户名
        HashEnv.put(Context.SECURITY_CREDENTIALS, password); // AD的密码
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
        HashEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");// 连接超时设置为3秒
        HashEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + post);// 默认端口389
        try {
            ctx = new InitialDirContext(HashEnv);// 初始化上下文
            log.info("鉴权成功");
            return 1;
        } catch (AuthenticationException e) {
            log.error("用户名或者密码错误");
            e.printStackTrace();
            return -1;
        } catch (javax.naming.CommunicationException e) {
            log.error("AD域连接失败!");
            e.printStackTrace();
            return -2;
        } catch (Exception e) {
            log.error("身份验证未知异常!");
            e.printStackTrace();
            return -3;
        } finally {
            if (null != ctx) {
                try {
                    ctx.close();
                    ctx = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //CNPC认证
  	public static int CNPCUserValid(String loginId, String password) {
  		ADServerInfo srvInfo = new ADServerInfo();
  		String domainName = "@cnpc.com.cn";
  		String ip = "cnpc.com.cn";
  		String port = "389";
  		String other = "ou=北京区域中心,dc=cnpc,dc=com,dc=cn";
  		srvInfo.setDomainName(domainName);
  		srvInfo.setPort(port+"/"+other);
  		srvInfo.setIpAddr(ip);
  		return ADProxy.authenticateByAD(srvInfo, loginId, password) == true ? 1 : -3;
  	
  	}
    public static boolean checkPassword(String password){
    	List<String> list = new ArrayList<String>();
    	list.add("[a-z]");
    	list.add("[A-Z]");
    	list.add("\\d");
    	list.add("[^A-Za-z0-9]");
    	Map<String,Object> result = new HashMap<String,Object>();
    	boolean flag = true;
    	StringBuffer msg = new StringBuffer();
    	if(password.length()<8) {
    		flag =false;
    		msg.append("密码长度小于8位;");
    	}else {
    		int num =0;
    		for(int i=0;i<list.size();i++) {
    			Pattern p = Pattern.compile(list.get(i));
    		    Matcher m = p.matcher(password); // 获取 matcher 对象
    			if(m.find()) {
    				num ++;
    			}
    		}
    		if(num<3) {
    			flag =false;
	    		msg.append("密码复杂度为:"+num+",不符合要求");
    		}
    		
    	}
    	result.put("flag", flag);
    	result.put("msg", msg);
    	log.info(result);
    	return flag;
    }
    public static void main ( String[] args ){
    	checkPassword("cqqkxxz_313959");
    }
}
