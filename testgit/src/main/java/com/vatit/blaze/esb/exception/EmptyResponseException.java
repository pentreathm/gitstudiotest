package com.vatit.blaze.esb.exception;

public class EmptyResponseException extends BlazeEsbException {
	
	private static final long serialVersionUID = -197321350859727094L;

	public EmptyResponseException(String objectToFetch, Throwable cause) {
		super(String.format("Unexpected empty response when fetching %s", objectToFetch), cause);
	}

	public EmptyResponseException(String objectToFetch) {
		this(objectToFetch, null);
	}

}
