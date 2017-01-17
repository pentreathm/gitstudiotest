package com.vatit.blaze.esb.transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vatit.blaze.dto.Connections;
import com.vatit.blaze.esb.exception.SimpleTransformerException;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class ConnectionsTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Connections connections;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			connections = mapper.readValue(message.getPayloadAsString(), new TypeReference<Connections>() {
			});
		} catch (Exception e) {
			throw new SimpleTransformerException(this, "Unable to deserialize Connections list.", e);
		}

		return connections;
	}

}
