package com.vatit.blaze.esb.dragon;

import com.vatit.blaze.dto.Client;
import com.vatit.blaze.esb.exception.SimpleTransformerException;
import com.vatit.wyvern.shared.dto.ClientDetailsOTDef;
import com.vatit.wyvern.shared.dto.getClientDetailsOTResponse;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class ClientResponseToClientTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		getClientDetailsOTResponse response = (getClientDetailsOTResponse)message.getPayload();

		if (response.getClients().getClientCoreList().size() == 0)
			throw new SimpleTransformerException(this, "Response received from Dragon with no Clients.");

		ClientDetailsOTDef dragonClient = response.getClients().getClientCore(0);
		Client client = new Client();

		client.setCode(dragonClient.getClientCode());
		client.setCountryCode(dragonClient.getCountryName());
		client.setId(dragonClient.getEntityCode());
		client.setName(dragonClient.getClientName());
		client.setReportingCurrencyCode(dragonClient.getReportingCurrency());

		return client;
	}

}
