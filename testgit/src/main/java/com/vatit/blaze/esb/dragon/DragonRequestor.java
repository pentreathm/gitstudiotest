package com.vatit.blaze.esb.dragon;

import com.vatit.wyvern.shared.dto.exception.WSCallException;
import org.apache.commons.httpclient.Cookie;
import org.mule.api.MuleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class DragonRequestor {

	@Value("${blaze.dragon.namespace}")
	private String namespace;
	@Value("${blaze.dragon.address}")
	private String serverAddress;
	@Autowired
	private DragonPropertyStore dragonPropertyStore;
	@Autowired
	private DragonLoggerInner loginRequestor;
	
	public static Cookie getDragonCookie(MuleMessage message) {
		Cookie[] cookies = message.getInboundProperty("cookies");
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies)
			if (cookie.getName().equals("AORTASECID"))
				return cookie;
		return null;
	}

	private Cookie[] loginAndReturnCookies() throws WSCallException {
		DragonLoginResponse response = loginRequestor.login();
		dragonPropertyStore.setServiceAccountCookies(response.getCookies());
		return response.getCookies();
	}
	
	private Cookie loginandReturnCookie() throws WSCallException {
		Cookie[] cookies = loginAndReturnCookies();
		return cookies[0];
	}
	
	private void clearCookie() {
		dragonPropertyStore.setServiceAccountCookies(null);
	}
	
	private Boolean hasCookie(Cookie[] cookies) {
		return (cookies != null && cookies.length > 0);
	}
	
	public Cookie getProcessedDragonCookie(MuleMessage message, Boolean useServiceAccount) throws WSCallException {
		Cookie[] cookies = message.getInvocationProperty("blaze.dragon.login.cookies"); // use blaze.dragon.login.cookies or iterate through cookies like in getDragonCookie at the top?
		if (hasCookie(cookies))
			return cookies[0];
		else
		{
			if (useServiceAccount) {
				cookies = dragonPropertyStore.getServiceAccountCookies();
				if (hasCookie(cookies)) {
					return cookies[0];
				}
				else 
					return loginandReturnCookie();
			}
			else
				return null; // can we return a null and allow the calling code to bomb out or should we raise an exception?
		}
	}

	private Object runMethod(MuleMessage message, String proxyClassName, String methodName, Boolean hasPayload, Cookie cookie, Boolean useServiceAccount) throws ClassNotFoundException, 
			InstantiationException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException, 
			NoSuchMethodException, SecurityException, WSCallException {
		
		Class<?> proxyClass = Class.forName(proxyClassName);
		
		Object proxy = proxyClass.getConstructor(String.class, String.class, String.class)
				.newInstance(namespace, String.format("http://%s", serverAddress), cookie == null ? "" : cookie.toString());
		
		Method method;
		try {
			if (hasPayload) {
				method = proxyClass.getMethod(methodName, message.getPayload().getClass()); 
				return method.invoke(proxy, message.getPayload());
			} else {
				method = proxyClass.getMethod(methodName); 
				return method.invoke(proxy);
			}
		} catch (Exception e) {
			if (DragonExceptionValidator.isInvalidCookieException(e.getMessage())) {
				clearCookie();
				return runMethod(message, proxyClassName, methodName, hasPayload, getProcessedDragonCookie(message, useServiceAccount), useServiceAccount);
			} else {
				throw e;
			}
		}
	}
	
	public Object call(MuleMessage message, String proxyClassName, String methodName, Boolean hasPayload, Boolean useServiceAccount) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, WSCallException {

		return runMethod(message, proxyClassName, methodName, hasPayload, getProcessedDragonCookie(message, useServiceAccount), useServiceAccount);
	}
}
