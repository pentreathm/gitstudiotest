package com.vatit.blaze.esb.exception;

public class UnhandledErrorPayloadException extends Exception {

	private static final long serialVersionUID = 8693972830462797596L;
	
	public UnhandledErrorPayloadException(String message) {
		super(String.format("%s\n\t(We don't know how to parse this error)", message));
	}
}
