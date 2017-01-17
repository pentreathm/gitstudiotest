package com.vatit.blaze.esb.dragon;

import org.apache.commons.httpclient.Cookie;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transport.http.CookieHelper;

public class ExtractDragonCookieTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// Could be sending the Dragon-Cookie header
		Object cookieProp = message.getInboundProperty("Dragon-Cookie");
		if (cookieProp != null) {
			Cookie[] cookies = CookieHelper.parseCookiesAsAServer(message.getInboundProperty("Dragon-Cookie").toString(), null);
			if (cookies.length > 0) 
				message.setInvocationProperty("blaze.dragon.login.cookies", cookies);
		}

		if (message.getInvocationProperty("blaze.dragon.login.cookies") == null) { // else check the cookies themselves
			Cookie cookie = DragonRequestor.getDragonCookie(message);
			if (cookie != null)
				message.setInvocationProperty("blaze.dragon.login.cookies", new Cookie[] {cookie});
		}
		
		return message.getPayload();
	}

}
