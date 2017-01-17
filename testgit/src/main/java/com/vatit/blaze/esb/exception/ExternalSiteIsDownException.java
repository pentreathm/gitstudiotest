package com.vatit.blaze.esb.exception;

public class ExternalSiteIsDownException extends BlazeEsbException {

	private static final long serialVersionUID = -267026047101286326L;

	public ExternalSiteIsDownException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalSiteIsDownException(String message) {
		super(message);
	}
	
	public ExternalSiteIsDownException(String site, String message) {
		this(site, message, null);
	}
	
	public ExternalSiteIsDownException(String site, String message, Throwable cause) {
		this(String.format("Oh Dear! We're tring to contact %s, and it is not available:\n%s", site, message), cause);
	}

}
