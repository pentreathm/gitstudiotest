package com.vatit.blaze.esb.dragon;

import org.apache.commons.httpclient.Cookie;
import org.springframework.stereotype.Component;

@Component
public class DragonPropertyStore {
	
	private volatile Cookie[] serviceAccountCookies;
	
	public Cookie[] getServiceAccountCookies() {
		return serviceAccountCookies;
	}

	public synchronized void setServiceAccountCookies(Cookie[] serviceAccountCookie) {
		this.serviceAccountCookies = serviceAccountCookie;
	}
}
