package com.vatit.blaze.esb.concur;

import org.mule.api.MuleMessage;

import com.vatit.blaze.esb.exception.HttpResponseException;
import com.vatit.blaze.utils.BlazeUtils;

public class ConcurExceptionValidator {

	public static boolean isPassableImageException(MuleMessage message) {
		try {
			HttpResponseException e = BlazeUtils.getFirstCauseOfType(message.getExceptionPayload().getException(), HttpResponseException.class);
			
			if (e != null && e.getStatusCode() == 404)
				return true;
		} catch (Exception e) {
			// Squash
		}
		return false;
	}

}
