package com.vatit.blaze.esb.dragon;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.vatit.blaze.esb.exception.SimpleTransformerException;
import com.vatit.wyvern.shared.dto.exception.WSCallException;

public class DragonServiceLoginTransformer extends AbstractMessageTransformer {

	private DragonLoggerInner requestor;
	
	@Override
	public void setMuleContext(MuleContext context) {
		super.setMuleContext(context);
		try {
			requestor = context.getRegistry().lookupObject(DragonLoggerInner.class);
		} catch (RegistrationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		try {
			DragonLoginResponse response =  requestor.login();
			message.setInvocationProperty("blaze.dragon.login.cookies", response.getCookies());
			return response;
		} catch (WSCallException e) {
			throw new SimpleTransformerException(this, "Error when logging into Dragon", e);
		}
	}
}
