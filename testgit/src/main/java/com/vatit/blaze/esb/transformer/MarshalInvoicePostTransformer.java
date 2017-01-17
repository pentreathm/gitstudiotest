package com.vatit.blaze.esb.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.vatit.blaze.dto.Views;

public class MarshalInvoicePostTransformer extends MarshalObjectTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Boolean hasCalcs = message.getInvocationProperty("blaze.wantsCalcs");
		Boolean hasExchangeRates = message.getInvocationProperty("blaze.wantsExchangeRates");
		setView(hasCalcs ? hasExchangeRates ? Views.InvoicePostResponseWithCalcsAndExchangeRates.class : Views.InvoicePostResponseWithCalcs.class : hasExchangeRates ? Views.InvoicePostResponseWithExchangeRates.class : Views.InvoicePostResponse.class);
		
		return super.transformMessage(message, outputEncoding);
	}
	
}
