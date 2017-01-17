package groovy

import com.vatit.blaze.dto.ConcurExpenseEntry
import com.vatit.blaze.dto.ConcurExpenseReport
import com.vatit.blaze.utils.BlazeUtils
import org.mule.api.MuleMessage

MuleMessage msg = message
ConcurExpenseReport expenseReport = msg.payload
List<ConcurExpenseEntry> errors = msg.getInvocationProperty('blaze.concur.entries.errors')

for (ConcurExpenseEntry entry : expenseReport.entries) {
	entry.errorMessage = BlazeUtils.validateInvoice(BlazeUtils.concurEntryToInvoice(entry));
	if (entry.errorMessage != null)
		errors.add(entry)
}

msg.payload