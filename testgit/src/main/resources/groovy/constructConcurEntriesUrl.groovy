package groovy

import com.vatit.blaze.dto.ConcurExpenseReport
import org.apache.http.client.utils.URIBuilder
import org.mule.api.MuleMessage

MuleMessage msg = message
ConcurExpenseReport expenseReport = msg.getInvocationProperty('blaze.concur.report')

uri = new URIBuilder('www.concursolutions.com/api/v3.0/expense/entries')
uri.addParameter('reportID', expenseReport.concurId)
uri.addParameter('user', 'ALL')
uri.addParameter('limit', msg.getInvocationProperty('blaze.get.invoicesInGroup.fromConcur.page.size').toString())

url = uri.build().toString()
offset = msg.getInvocationProperty('blaze.concur.entries.offset')
if (offset != null)
	url = "$url&offset=$offset"
msg.setInvocationProperty('blaze.concur.pageUrl', url)

msg.payload