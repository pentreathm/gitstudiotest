package com.vatit.blaze.esb.dragon;

import org.mule.api.MuleMessage;
import com.vatit.blaze.esb.dragon.dto.Fault;

public class DragonExceptionValidator {

	public static boolean isInvalidCookieException(MuleMessage message) {
		try {
			if (message.getPayload() instanceof Fault) {
				Fault fault = (Fault)(message.getPayload()); 
				if (fault.getDetail() != null && fault.getDetail().error != null) {
					if (fault.getDetail().error.textValue.equals("Invalid Login") || fault.getDetail().error.textValue.contains("Web Service call does not have credentials and is NOT Authorised"))
						return true;
				}
			}
		} catch (Exception e) {
			// Squash
		}
		return false;
	}
	
	public static boolean isInvalidCookieException(String errorText) {
		return (errorText != null && (errorText.contains("Invalid Login") || errorText.contains("Web Service call does not have credentials and is NOT Authorised"))); 
	}
}
