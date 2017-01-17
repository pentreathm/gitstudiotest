package com.vatit.blaze.esb.transformer;

import com.vatit.blaze.dto.ExpenseType;
import com.vatit.blaze.dto.ExpenseTypes;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class ReconcileExpenseTypesTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		ExpenseTypes toBeResolved = message.getInvocationProperty("expenseTypesMissingDragonId");
		ExpenseTypes resolved = (ExpenseTypes) message.getPayload();

		for (ExpenseType toBeResolvedItem : toBeResolved.getItems()) {
			ExpenseType resolvedItem = null;
			for (ExpenseType search : resolved.getItems()) {
				if (search.getOrchestrationId().equals(toBeResolvedItem.getOrchestrationId())) {
					resolvedItem = search;
					break;
				}
			}

			if (resolvedItem != null)
				toBeResolvedItem.setDragonId(resolvedItem.getDragonId());
		}

		return message.getPayload();
	}


}
