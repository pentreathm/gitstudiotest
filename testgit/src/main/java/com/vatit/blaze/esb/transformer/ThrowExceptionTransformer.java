package com.vatit.blaze.esb.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * Throws an exception on demand.
 */
public class ThrowExceptionTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Boolean throwException = message.getInvocationProperty("throwException");
		if (throwException) {
			String errorMessage = message.getInvocationProperty("errorMessage");
			String errorCode = message.getInvocationProperty("errorCode");
			throw new RuntimeException(errorMessage + errorCode);
		}
		return message.getPayload();
	}

}
