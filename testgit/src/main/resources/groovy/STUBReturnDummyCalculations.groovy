package main.resources.groovy
// if any invoices have no client id, then
//    set payload to stub payload
//    set blaze.stubbed=true

import com.vatit.blaze.dto.Invoices;
import com.vatit.blaze.dto.Invoice
import com.vatit.blaze.exception.MissingFieldException;

Invoices invoices = payload

for (Invoice invoice : invoices.items) {
	if (invoice.connection?.client?.id == null && invoice.connection?.client?.countryCode == null)
		throw new MissingFieldException("Missing required field: client Id or client country code; for invoice calculations.")
	else if (invoice.connection?.client?.id == null) {
		message.setInvocationProperty('blaze.stubbed', true)
		invoice.approximateReturnPercentage = (new Random().nextInt(10) + 10) / 100
		invoice.calculationComments = 'Random generated return - Blaze stub'
	}
}

payload