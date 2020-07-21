package com.utils;

import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ADProxy {
	private static final Logger log = LoggerFactory.getLogger(ADProxy.class);

	public static boolean authenticateByAD(ADServerInfo adServer, String username, String password) {
		boolean result = false;
		Context ctx = null;
		if (adServer != null) {
			Properties props = new Properties();
			props.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			props.put("java.naming.provider.url", "ldap://" + adServer.getIpAddr() + ":" + adServer.getPort());
			props.put("java.naming.security.authentication", "simple");
			props.put("java.naming.security.principal", username + adServer.getDomainName());
			if (password == null || password.equals("")) {
				return false;
			}

			props.put("java.naming.security.credentials", password);

			try {
				ctx = new InitialLdapContext(props, (Control[]) null);
				result = true;
			} catch (AuthenticationException var8) {
				//var8.printStackTrace();
			} catch (NamingException var9) {
				var9.printStackTrace();
				log.info("NamingException");
			}

			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException var7) {
					//var7.printStackTrace();
				}
			}
		}

		return result;
	}

//	public static void main(String[] args) {
//		ADServerInfo server = new ADServerInfo();
//		server.setDomainName("@cnpc.com.cn");
//		server.setIpAddr("10.62.60.2");
//		server.setPort("389");
//		System.out.println(authenticateByAD(server, "dingyongli", ""));
//	}
}