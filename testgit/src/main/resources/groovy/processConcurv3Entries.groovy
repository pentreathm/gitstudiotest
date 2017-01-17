package groovy

import com.vatit.blaze.dto.ConcurExpenseEntries
import com.vatit.blaze.dto.ConcurExpenseEntry
import com.vatit.blaze.dto.ConcurExpenseReport
import org.apache.commons.lang3.StringUtils
import org.mule.api.MuleMessage
import org.mule.api.transport.PropertyScope

MuleMessage msg = message
ConcurExpenseEntries entries = msg.payload
String nextPage = entries.nextPage

// Copy all entries to the expense report
ConcurExpenseReport expenseReport = msg.getInvocationProperty('blaze.concur.report')
expenseReport.entries.addAll(entries.items)

// Extract the paging offset
if (StringUtils.isNotEmpty(nextPage)) {
	waterline = nextPage =~ /offset=([^&]*)/
	msg.setInvocationProperty('blaze.concur.entries.offset', waterline[0][1])
} else
	msg.removeProperty('blaze.concur.entries.offset', PropertyScope.INVOCATION)

return expenseReport