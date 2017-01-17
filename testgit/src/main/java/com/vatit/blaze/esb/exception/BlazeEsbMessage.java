package com.vatit.blaze.esb.exception;

import org.mule.config.i18n.Message;

public class BlazeEsbMessage extends Message {
	
	private static final long serialVersionUID = 2675062775066352420L;

	public BlazeEsbMessage(String message, int code, Object... arg) {
		super(message, code, arg);
	}

}
