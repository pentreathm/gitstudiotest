package com.vatit.blaze.esb.transformer;

import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.util.Base64;
import org.mule.util.IOUtils;

import java.io.InputStream;

/**
 * Use this transformer rather than the Mule one if you don't want to close the input stream.
 */
public class Base64EncoderInputStreamTransformer extends AbstractTransformer {

	public Base64EncoderInputStreamTransformer() {
		registerSourceType(DataTypeFactory.INPUT_STREAM);
	}

	@Override
	public Object doTransform(Object src, String encoding) throws TransformerException {
		try {
			InputStream input = (InputStream) src;
			byte[] buf = IOUtils.toByteArray(input);
			String result = Base64.encodeBytes(buf, Base64.DONT_BREAK_LINES);

			if (getReturnClass().equals(byte[].class)) {
				return result.getBytes(encoding);
			} else {
				return result;
			}
		} catch (Exception ex) {
			throw new TransformerException(
				CoreMessages.transformFailed(src.getClass().getName(), "base64"), this, ex);
		}
	}

}
