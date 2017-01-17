package com.vatit.blaze.esb.sharepoint;

import org.mule.api.MuleMessage;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatit.blaze.utils.BlazeUtils;
import com.vatit.blaze.esb.sharepoint.dto.SharepointError;

import org.mule.module.sharepoint.exception.SharepointException;

public class SharepointExceptionValidator {

	public static boolean isExistingFileException(MuleMessage message) {
		try {
			SharepointException e = BlazeUtils.getFirstCauseOfType(message.getExceptionPayload().getException(), SharepointException.class);
			if (e != null && e.getMessage() != null) {
				String spMessage = e.getMessage().replace("Code: 400 Message: ", "");
				ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
				SharepointError spError = mapper.readValue(spMessage, SharepointError.class);
				if (spError.getCode().contains("-2130575257"))
					return true;
			}
		} catch (Exception e) {
			// Squash
		}
		return false;
	}
}
