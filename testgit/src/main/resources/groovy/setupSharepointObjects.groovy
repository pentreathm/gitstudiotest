package groovy

import com.vatit.blaze.dto.Document
import com.vatit.blaze.utils.MimeTypes

Document document = message.getInvocationProperty('blaze.document')
document.mimeType = message.getInvocationProperty('blaze.document.content.type')
document.extension = MimeTypes.getExt(document.mimeType)

message.setInvocationProperty('blaze.sharepoint.file.name', "${document.externalId} [${document.sourceCode}].${document.extension}")

// Switches the payload to document body
return message.getInvocationProperty('blaze.document.content')
