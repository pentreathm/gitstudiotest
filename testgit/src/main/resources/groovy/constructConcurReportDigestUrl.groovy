package groovy

import com.vatit.blaze.dto.ConcurConnection
import groovy.time.TimeCategory
import org.apache.http.client.utils.URIBuilder

ConcurConnection connection = message.getInvocationProperty("blaze.connection")
if (connection.reportDigestWaterline != null){
    url = connection.reportDigestWaterline
} else {
    uri = new URIBuilder('www.concursolutions.com/api/v3.0/expense/reports')
    uri.addParameter('limit', message.getInvocationProperty('blaze.page.size').toString())
    uri.addParameter('user', 'ALL')
		uri.addParameter('paymentStatusCode', 'P_PAID,P_PAYC')

		url = uri.build().toString().replace("%252C", "%2C")

		int maxYearsLocal = maxYears
    now = new Date()
    use(TimeCategory) {
        paidDateAfter = connection.lastSyncTime != null ? (connection.lastSyncTime + 1.second).format('yyyy-MM-dd\'T\'HH:mm:ss') : (now - 2.years).format('yyyy-MM-dd\'T\'HH:mm:ss')
    }
    url = "$url&PaidDateAfter=$paidDateAfter"
}

message.setInvocationProperty('ConcurPageURL', url)

payload