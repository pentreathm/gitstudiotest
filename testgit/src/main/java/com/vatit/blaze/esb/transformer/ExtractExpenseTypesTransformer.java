package com.vatit.blaze.esb.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import com.vatit.blaze.dto.Invoice;
import com.vatit.blaze.dto.Invoices;
import com.vatit.blaze.dto.ExpenseTypes;
import com.vatit.blaze.dto.ExpenseType;
import com.vatit.blaze.comparators.SearchUtils;

import java.util.Objects;
import java.util.UUID;


public class ExtractExpenseTypesTransformer extends AbstractMessageTransformer {
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		ExpenseTypes uniqueExpenseTypesMissingDragonId = new ExpenseTypes();
		ExpenseTypes expenseTypesMissingDragonId = new ExpenseTypes();
		Invoices invoices = (Invoices)message.getPayload();

		for (Invoice item : invoices.getItems()) {

			//If the dragon expense type id is null we need to fetch it from services
			if (item.getExpenseType().getDragonId() == null) {
				//We need either the expense type external id or blaze expense type id to resolve the dragon id
				if (item.getExpenseType().getExternalId() == null && item.getExpenseType().getId() == null)
					throw new com.vatit.blaze.esb.exception.SimpleTransformerException(this, "Invoices sent for calculation must specify an expense type with an expense type external id,  or expense type Dragon id.");
				
				ExpenseType expenseType = null;
				
				// Find existing expense types
				for (ExpenseType search : uniqueExpenseTypesMissingDragonId.getItems()) {
					if (Objects.equals(search.getExternalId(), item.getExpenseType().getExternalId()) && SearchUtils.Connected.isEqual(search, item)) {
						expenseType = search;
						break;
					}
				}

				//if this is the first occurrence of the expense type we create a UUID for it and assign the connection details
				if (expenseType == null) {
					expenseType = new ExpenseType();
					expenseType.setExternalId(item.getExpenseType().getExternalId());
					expenseType.setId(item.getExpenseType().getId());
					expenseType.setConnection(item.getConnection());
					expenseType.setOrchestrationId(UUID.randomUUID().toString());
					uniqueExpenseTypesMissingDragonId.getItems().add(expenseType);
				}

				item.getExpenseType().setOrchestrationId(expenseType.getOrchestrationId());

				expenseTypesMissingDragonId.getItems().add(item.getExpenseType());
			}
		}

		message.setInvocationProperty("expenseTypesMissingDragonId", expenseTypesMissingDragonId);
		
		return uniqueExpenseTypesMissingDragonId;
	}


}
