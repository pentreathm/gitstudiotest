package com.vatit.blaze.esb.transformer;

import org.apache.commons.httpclient.HttpStatus;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

public class HttpErrorResponseCheckerAllowingNotFound extends HttpErrorResponseChecker {
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		if (getHttpStatus(message) == HttpStatus.SC_NOT_FOUND)
			return message.getPayload();
		return super.transformMessage(message, outputEncoding);
	}
}
