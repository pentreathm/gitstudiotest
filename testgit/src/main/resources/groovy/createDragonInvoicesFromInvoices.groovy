package groovy

import com.vatit.blaze.dto.Invoice
import com.vatit.blaze.dto.Invoices
import com.vatit.wyvern.shared.dto.InvoiceCalculationDef
import com.vatit.wyvern.shared.dto.InvoiceDetailsListOTDef
import com.vatit.wyvern.shared.dto.InvoiceDetailsOTDef
import com.vatit.wyvern.shared.dto.calculateInvoiceValueOTRequest
import org.mule.api.MuleMessage

def mapBlazeInvoiceToDragonInvoice = { Invoice inv ->
	newInvoice = new InvoiceDetailsOTDef()
	newInvoice.invoiceDate = inv.date
	newInvoice.invoiceAmount = inv.amount
	newInvoice.invoiceID = inv.id ?: inv.externalId
	if (newInvoice.invoiceID == null)
		throw new com.vatit.blaze.exception.MissingFieldException("Invoices sent for calculation must specify either an Id or External Id.");

	newInvoice.invoiceCountry = inv.countryCode?.trim()
	newInvoice.region = inv.regionCode
	newInvoice.clientCountry = inv.connection?.client?.countryCode?.trim()
	newInvoice.invoiceExpenseType = inv.expenseType?.dragonId
	newInvoice.invoiceValid = true
	newInvoice
}

MuleMessage msg = message

request = new calculateInvoiceValueOTRequest()
newGroup = request.invoiceCalculation = new InvoiceCalculationDef()
newGroup.setInvoices(InvoiceDetailsListOTDef.create())


invoices = (List<Invoice>)msg.payload
//If we have a client id we use it
if (invoices[0].connection?.client?.id != null)
	newGroup.clientCode = invoices[0].connection.client.id
//if we do not have a client id there must be a client country code
else if (invoices[0].connection?.client?.countryCode == null)
	throw new com.vatit.blaze.exception.MissingFieldException("Invoices sent for calculation must specify either a Client Id or Client Country Code.");

//Unmapped Expense Types
Invoices unmapped = new Invoices()

for (Invoice item : invoices) {

	// Only calculate if the invoice is ready to be calculated
	if (item.expenseType?.dragonId == null)
		item.calculationComments = 'Calculation skipped: expense type is not mapped'
	else if (item.date == null)
		item.calculationComments = 'Calculation skipped: no invoice date'
	else {
		newGroup.invoices.addInvoice(mapBlazeInvoiceToDragonInvoice(item))
		// pre-emptive, incase dragon does not send back the response
		item.calculationComments = 'Calculation results not received from Dragon'
	}

	// Always set calc error to true - when Dragon results are correctly reconciled, it will get set back to false
	item.hasCalculationError = true

	if (item.expenseType?.dragonId == null && unmapped != null)
		unmapped.items.add(item)
}

msg.getInvocationProperty("expense.types.unmapped").items = unmapped.items

return request
