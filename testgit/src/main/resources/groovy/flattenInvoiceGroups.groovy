import com.vatit.blaze.dto.Invoice
import com.vatit.blaze.dto.InvoiceGroup
import com.vatit.blaze.dto.InvoiceGroups
import com.vatit.blaze.utils.BlazeUtils.ImportOperation;

result = new ArrayList<Invoice>()

groups = payload instanceof InvoiceGroups ? payload.items : payload
for (InvoiceGroup group : groups)
	for (Invoice invoice : group.invoices)
		if (invoice.whatHappened != ImportOperation.DELETED) {
			invoice.connection = group.connection
			result.add invoice
		}

result