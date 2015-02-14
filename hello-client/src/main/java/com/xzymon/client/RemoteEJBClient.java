package com.xzymon.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.xzymon.hello.CountBean;
import com.xzymon.hello.CountRemote;

public class RemoteEJBClient {
	
	public static final void main(String[] args) throws Exception{
		invokeCountRemote();
	}

	private static void invokeCountRemote() throws NamingException {
		CountRemote count = lookupCountRemote();
		String param = "Something";
		System.out.format("Answer for '%1$s': %2$d%n", param, count.countLetters(param));
	}
	
	private static CountRemote lookupCountRemote() throws NamingException{
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		
		String appName = "hello";
		String moduleName = "hello-ejb";
		String distinctName = "";
		String beanName = CountBean.class.getSimpleName();
		String viewClassName = CountRemote.class.getName();
		String lookupName = String.format("ejb:%1$s/%2$s/%3$s/%4$s!%5$s", appName, moduleName, distinctName, beanName, viewClassName);
		
		return (CountRemote) context.lookup(lookupName);
	}
}
