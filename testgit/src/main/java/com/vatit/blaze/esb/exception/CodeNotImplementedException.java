package com.vatit.blaze.esb.exception;

public class CodeNotImplementedException extends BlazeEsbException {

	private static final long serialVersionUID = 4008713713730748301L;

	public CodeNotImplementedException(String what) {
		this(what, null);
	}
	
	public CodeNotImplementedException(String what, Throwable cause) {
		super(String.format("[Development Error] Missing Expected Implementation: %s", what), cause);
	}
	
	
}
