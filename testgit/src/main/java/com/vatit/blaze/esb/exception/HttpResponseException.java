package com.vatit.blaze.esb.exception;

import org.apache.commons.httpclient.HttpStatus;

public class HttpResponseException extends BlazeEsbException {

	private static final long serialVersionUID = 5479998749012806921L;

	private Integer statusCode;
	public String getStatusText() {
		return HttpStatus.getStatusText(statusCode);
	}
	public HttpResponseException(Integer statusCode, Throwable cause) {
		super(String.format("%d - %s", statusCode, HttpStatus.getStatusText(statusCode)), cause);
		this.statusCode = statusCode;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
}
