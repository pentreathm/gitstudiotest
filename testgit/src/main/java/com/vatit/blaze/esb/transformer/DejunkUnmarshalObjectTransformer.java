package com.vatit.blaze.esb.transformer;

import com.vatit.blaze.esb.utils.JsonDejunker;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

/**
 * Calls TrimLeadingJunkJsonTransformer and UnmarshalObjectTransformer in that order.
 */
public class DejunkUnmarshalObjectTransformer extends UnmarshalObjectTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String payload = (String) message.getPayload();
		String dejunkedPayload = JsonDejunker.dejunk(payload);
		message.setPayload(dejunkedPayload);
		return super.transformMessage(message, outputEncoding);
	}
}
