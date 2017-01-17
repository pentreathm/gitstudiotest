package com.vatit.blaze.esb.transformer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.vatit.blaze.esb.exception.SimpleTransformerException;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * Transforms the payload in JSON format into an object of type <code>returnClassName</code>.
 */
public class UnmarshalObjectTransformer extends AbstractMessageTransformer {

	private String returnClassName;
	private Boolean jsonIsPascalCase = false;

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		if (returnClassName == null)
			throw new SimpleTransformerException(this, String.format("ReturnClassName must be specified for %s.", this.getClass().getSimpleName()));

		String payload;
		try {
			payload = message.getPayloadAsString();
		} catch (Exception e) {
			throw new SimpleTransformerException(this, String.format("Unable to convert payload to string, during unmarshal to %s.", returnClassName), e);
		}
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
		if (getJsonIsPascalCase())
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		Object unmarshalledPayload;
		try {
			unmarshalledPayload = mapper.readValue(payload, Class.forName(getReturnClassName()));
		} catch (Exception e) {
			throw new SimpleTransformerException(this, String.format("Unable to unmarshal json payload to %s.", returnClassName), e);
		}
		return unmarshalledPayload;
	}

	private String getReturnClassName() {
		return returnClassName;
	}

	public void setReturnClassName(String returnClassName) {
		this.returnClassName = returnClassName;
	}

	public Boolean getJsonIsPascalCase() {
		return jsonIsPascalCase;
	}

	public void setJsonIsPascalCase(Boolean jsonIsPascalCase) {
		this.jsonIsPascalCase = jsonIsPascalCase;
	}

}
