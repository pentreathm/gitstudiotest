package com.vatit.blaze.esb.exception;

import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;

public class BlazeEsbTransformerException extends TransformerException {

	private static final long serialVersionUID = 8674767838903169301L;

	public BlazeEsbTransformerException(Transformer transformer, BlazeEsbMessage message, Throwable cause) {
		super(message, transformer, cause);
	}
}
