package com.vatit.blaze.esb.ocr;

import com.vatit.blaze.dto.xpenditureOcr.SyncStatusRequest;
import com.vatit.blaze.dto.xpenditureOcr.SyncStatusRequestOcrProcess;
import com.vatit.blaze.dto.xpenditureOcr.VatCheckGetResultsResponse;
import com.vatit.blaze.dto.xpenditureOcr.VatCheckGetResultsResponseOcrProcess;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import java.util.HashSet;

public class CreateSyncStatusRequestTransformer extends AbstractMessageTransformer {

	private String apiKey;

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		VatCheckGetResultsResponse vatCheckGetResultsResponse = (VatCheckGetResultsResponse) message.getPayload();
		HashSet<String> successfullyProcessedImages = message.getInvocationProperty("successfullyProcessedImages");
		SyncStatusRequest syncStatusRequest = (SyncStatusRequest) new SyncStatusRequest().withApiKey(getApiKey());
		for (VatCheckGetResultsResponseOcrProcess getResultsOcrProcess : vatCheckGetResultsResponse.getOcrProcesses()) {
			if (successfullyProcessedImages.contains(getResultsOcrProcess.getExternalProcessId())) {
				syncStatusRequest.addOcrProcess(new SyncStatusRequestOcrProcess()
					.withExternalProcessId(getResultsOcrProcess.getExternalProcessId())
					.withKeepProcess(false));
				// We are not using VAT extraction at this stage, so we have no reason to keep the OCR process regardless of whether VAT was found on the image or not
			}
		}
		return syncStatusRequest;
	}

	private String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
