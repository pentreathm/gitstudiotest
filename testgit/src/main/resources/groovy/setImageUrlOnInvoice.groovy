import org.mule.api.transport.PropertyScope
import org.mule.modules.sharepoint.microsoft.copy.CopyResultCollection
import javax.xml.ws.Holder

Holder<CopyResultCollection> spResult = message.getProperty('blaze.sharepoint.result', PropertyScope.INVOCATION)

message.payload.setImageUrl spResult.value.copyResult[0].destinationUrl 

return message.payload