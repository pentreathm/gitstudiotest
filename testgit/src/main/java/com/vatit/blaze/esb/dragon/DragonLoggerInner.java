package com.vatit.blaze.esb.dragon;

import com.vatit.wyvern.server.service.wsproxy.HttpWSCall;
import com.vatit.wyvern.server.service.wsproxy.WSCallRequest;
import com.vatit.wyvern.server.service.wsproxy.WSCallResult;
import com.vatit.wyvern.server.service.wsproxy.WSProxy;
import com.vatit.wyvern.shared.dto.CredentialsDef;
import com.vatit.wyvern.shared.dto.LoginRequest;
import com.vatit.wyvern.shared.dto.PasswordDef;
import com.vatit.wyvern.shared.dto.exception.WSCallException;
import org.apache.commons.httpclient.Cookie;
import org.joda.time.DateTime;
import org.mule.transport.http.CookieHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DragonLoggerInner {
	
	@Value("${blaze.ase.namespace}")
	private String namespace;
	@Value("${blaze.dragon.address}")
	private String serverAddress;
	@Value("${blaze.dragon.username}")
	private String username;
	@Value("${blaze.dragon.password}")
	private String password;

	public DragonLoginResponse login() throws WSCallException {
		DragonLoginResponse response = new DragonLoginResponse();
		
		LoginRequest body = new LoginRequest();
		body.setCredentials(new CredentialsDef());		
		body.getCredentials().setLoginName(username);
		body.getCredentials().setPassword(new PasswordDef());
		body.getCredentials().getPassword().setElementData(password);
		
		WSCallRequest loginRequest = HttpWSCall.createRequest(WSProxy.Protocol.IXML1);
		loginRequest.setAction(namespace);
		loginRequest.setCallName("login");
		loginRequest.setCallBody(body);

		try {
			WSCallResult loginResponse = HttpWSCall.call(loginRequest, String.format("http://%s", serverAddress), "");
			Cookie[] cookies = CookieHelper.parseCookiesAsAClient(loginResponse.getHeaderFields().get("Set-Cookie").get(0), null);
			
			DateTime expiry = new DateTime().plusMinutes(360);

			for (Cookie cookie : cookies)
				if (cookie.getExpiryDate() == null)
					cookie.setExpiryDate(expiry.toDate());
			
			response.setCookies(cookies);
			
		}	catch( Exception e ) {
      throw new WSCallException("Error while logging into Dragon", e);
    }
		return response;
	}

}
