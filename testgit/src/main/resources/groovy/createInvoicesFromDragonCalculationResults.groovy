package groovy

import com.vatit.blaze.dto.Invoice
import com.vatit.blaze.exception.ResourceNotFoundException
import com.vatit.blaze.utils.Constants
import com.vatit.wyvern.shared.dto.InvoiceDetailsListOTDef
import com.vatit.wyvern.shared.dto.InvoiceDetailsOTDef
import org.mule.api.MuleMessage

MuleMessage msg = message
InvoiceDetailsListOTDef calcs = msg.getInvocationProperty('dragon.calculations')
blazeInvoices = (List<Invoice>)msg.payload

def nonZeroCount = 0

if (calcs != null) {
	calcs.invoiceList.each {

		InvoiceDetailsOTDef dragonInvoice = it
		Invoice invoice = blazeInvoices.find { it.id?.toString() == dragonInvoice.invoiceID || it.externalId == dragonInvoice.invoiceID }
		if (invoice == null)
			throw new ResourceNotFoundException(Constants.ObjectName.Invoice, dragonInvoice.invoiceID)

		invoice.calculationComments = dragonInvoice.calculationComment
		invoice.hasCalculationError = false
		invoice.approximateReturnPercentage = dragonInvoice.percentClaimable ?: 0
		invoice.taxPercentage = dragonInvoice.VATRate
		invoice.regionalTaxPercentage = dragonInvoice.regionTaxRate
		if (invoice.approximateReturnPercentage != 0)
			nonZeroCount += 1
	}
}

log.info "Dragon returned ${calcs?.sizeInvoice() ?: 0} calculations, $nonZeroCount of which have non-zero Percent Claimable."

blazeInvoices