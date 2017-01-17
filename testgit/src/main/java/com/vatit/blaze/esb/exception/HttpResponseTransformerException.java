package com.vatit.blaze.esb.exception;

import org.apache.commons.httpclient.HttpStatus;
import org.mule.api.transformer.Transformer;

import com.vatit.blaze.utils.BlazeUtils;

public class HttpResponseTransformerException extends BlazeEsbTransformerException {

	private static final long serialVersionUID = 9160863536278871843L;
	
	private Boolean surfaceHttpStatus;
	private Integer httpStatus;

	public HttpResponseTransformerException(Transformer transformer, Integer statusCode, String message, Boolean surfaceHttpStatus, Throwable cause) {
		super(transformer, new BlazeEsbMessage(message, statusCode, HttpStatus.getStatusText(statusCode)), cause);
		this.surfaceHttpStatus = surfaceHttpStatus;
		this.httpStatus = statusCode;
	}

	public Boolean getSurfaceHttpStatus() {
		return surfaceHttpStatus;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}
	
	public static Boolean surfaceStatus(Throwable exception)
	{		
		try {
			HttpResponseTransformerException e = BlazeUtils.getFirstCauseOfType(exception, HttpResponseTransformerException.class);
			if(e.surfaceHttpStatus) return true;
			
		} catch (Exception e) {
			// Squash
		}
		return false;
	}
	
	public static Integer getHttpStatus(Throwable exception)
	{		
			HttpResponseTransformerException e = BlazeUtils.getFirstCauseOfType(exception, HttpResponseTransformerException.class);
			return e.getHttpStatus();
	}
	
}
