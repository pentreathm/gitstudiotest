package com.vatit.blaze.esb.concur;

import com.vatit.blaze.esb.exception.BlazeEsbException;

public class ConcurException extends BlazeEsbException {
	
	private static final long serialVersionUID = -2004653478358024695L;

	public ConcurException(String message, Throwable cause) {
		super(String.format("Error when calling Concur: %s", message), cause);
	}
	
	public ConcurException(String message) {
		this(message, null);
	}
}
