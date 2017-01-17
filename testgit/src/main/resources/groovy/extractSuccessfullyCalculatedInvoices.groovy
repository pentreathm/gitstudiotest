package groovy

import com.vatit.blaze.dto.Invoice
import com.vatit.blaze.dto.Invoices

List<Invoice> calculated = new ArrayList<>()

for (Invoice invoice : payload.items)
	if (!invoice.hasCalculationError)
		calculated.add(invoice);

return new Invoices(calculated);
