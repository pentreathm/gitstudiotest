package com.vatit.blaze.esb.ocr;

import com.vatit.blaze.dto.xpenditureOcr.SyncStatusResponse;
import com.vatit.blaze.dto.xpenditureOcr.SyncStatusResponseOcrProcess;
import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class LogSyncStatusFailuresTransformer extends AbstractMessageTransformer {

	public static Logger logger = Logger.getLogger(LogSyncStatusFailuresTransformer.class);

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		SyncStatusResponse syncStatusResponse = (SyncStatusResponse) message.getPayload();
		if (syncStatusResponse.getFailedUpdates() != null) {
			StringBuffer syncFailures = new StringBuffer();
			for (SyncStatusResponseOcrProcess syncStatusResponseOcrProcess : syncStatusResponse.getFailedUpdates()) {
				if (syncFailures.length() > 0) {
					syncFailures.append(", ");
				}
				syncFailures.append(syncStatusResponseOcrProcess.getExternalProcessId());
				syncFailures.append("(error code ");
				syncFailures.append(syncStatusResponseOcrProcess.getErrorCode());
				syncFailures.append(")");
			}
			logger.error("These processes have failed to sync: " + syncFailures.toString());
		}
		return message.getPayload();
	}
}
