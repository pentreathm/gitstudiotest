package com.vatit.blaze.esb.transformer;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.compression.GZipCompressTransformer;

public class ObjectCompressorTransformer extends AbstractMessageTransformer {

	public static Logger log = Logger.getLogger(ObjectCompressorTransformer.class);
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		try {
			String acceptEncodingHeader = message.getProperty("blaze.responseAcceptEncoding", PropertyScope.INVOCATION);
			Object obj = message.getPayload();
			//First check to see if there is an accept-encoding header
			if( acceptEncodingHeader!= null)
			{
				//Next check if it specifies gzip
				if(acceptEncodingHeader.toLowerCase().contains("gzip"))
				{
					//compress payload
					log.trace("Compress payload to gzip");
					GZipCompressTransformer compressor = new GZipCompressTransformer();
					Object compressedPayload = compressor.doTransform(obj, "UTF-8");
					message.setOutboundProperty("Content-Encoding", "gzip");
					return compressedPayload;
				}
			}
			//else return the payload as is (no compression)
			return message.getPayload(); 
			
		} catch (Exception e) {
			e.printStackTrace();
			message.setOutboundProperty("ObjectCompressorErrorMsg", e.toString());
			throw new TransformerException(MessageFactory.createStaticMessage("Compression Exception: " + e.toString()));
		}
	}
}