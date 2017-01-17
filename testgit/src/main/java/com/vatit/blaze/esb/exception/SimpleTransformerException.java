package com.vatit.blaze.esb.exception;

import org.mule.api.transformer.Transformer;

public class SimpleTransformerException extends BlazeEsbTransformerException {

	private static final long serialVersionUID = 5402983028172778274L;
	
	public SimpleTransformerException(Transformer transformer, String message) {
		this(transformer, message, null);
	}

	public SimpleTransformerException(Transformer transformer, String message, Throwable cause) {
		super(transformer, new BlazeEsbMessage(message, 0), cause);
	}

}
