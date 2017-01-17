package groovy

import com.vatit.blaze.dto.ConcurExpenseEntry
import com.vatit.blaze.dto.ConcurExpenseReport
import com.vatit.blaze.esb.SyncReport
import com.vatit.blaze.esb.SyncReportConnection
import com.vatit.blaze.esb.exception.BlazeEsbException
import org.mule.api.MuleMessage

MuleMessage msg = message;

SyncReport report = msg.getInvocationProperty('sync.report')
SyncReportConnection connection = report.connections.get(msg.getInvocationProperty('myCount') - 1)

ConcurExpenseReport expenseReport = msg.getInvocationProperty('blaze.concur.report')
connection.entriesRetrieved += expenseReport.entries.size()

List<ConcurExpenseEntry> faults =  msg.getInvocationProperty('blaze.concur.entries.errors')
if (faults.size() > 0) {
	connection.faultyInvoices += faults.size()

	String errorMessage = "On Report ${expenseReport.concurId}, with Owner ${expenseReport.ownerLoginId}, there were ${faults.size()} out of ${expenseReport.entries.size()} Entries with errors:"
	for (ConcurExpenseEntry error : faults)
		errorMessage += "\n" + error.errorMessage
	connection.warnings.add errorMessage

	maxErrors = msg.getInvocationProperty('invoice.group.error.maxcount')
	if (connection.faultyInvoices >= maxErrors && maxErrors > 0)
		throw new BlazeEsbException("${connection.faultyInvoices} faulty Entries were found, which exceeds the maximum allowance of $maxErrors")
}

msg.payload