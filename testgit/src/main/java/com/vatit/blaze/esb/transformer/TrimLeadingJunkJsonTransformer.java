package com.vatit.blaze.esb.transformer;

import com.vatit.blaze.esb.utils.JsonDejunker;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * Removes all leading characters from the payload before the first '{' character. The payload is assumed to be in JSON format.
 */
public class TrimLeadingJunkJsonTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String payload = (String) message.getPayload();
		String trimmedPayload = JsonDejunker.dejunk(payload);
		return trimmedPayload;
	}

}
