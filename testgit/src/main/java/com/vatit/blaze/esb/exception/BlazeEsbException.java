package com.vatit.blaze.esb.exception;

public class BlazeEsbException extends Exception {

	private static final long serialVersionUID = 8674767838903169301L;

	public BlazeEsbException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BlazeEsbException(String message) {
		super(message);
	}
}
