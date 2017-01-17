package groovy

import com.vatit.blaze.dto.Document
import com.vatit.blaze.esb.SyncReport
import com.vatit.blaze.esb.SyncReportConnection
import org.mule.api.MuleMessage;

MuleMessage msg = message
Document document = msg.getInvocationProperty('blaze.document')

SyncReport report = msg.getInvocationProperty('sync.report')
SyncReportConnection connection = report.connections.get(msg.getInvocationProperty('myCount') - 1)
connection.imagesNotFound.add("Images Level: Document externalId = " + document.externalId)
connection.faultyImages += 1

document.status = Document.Status.X;
document.statusText = 'Not found'

return document
