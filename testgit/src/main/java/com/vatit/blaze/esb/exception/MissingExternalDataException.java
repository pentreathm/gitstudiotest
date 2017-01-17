package com.vatit.blaze.esb.exception;

public class MissingExternalDataException extends BlazeEsbException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingExternalDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingExternalDataException(String message) {
		super(message);
	}

}
