package com.vatit.blaze.esb.transformer;

import java.io.StringWriter;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractMessageTransformer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MarshalObjectTransformer extends AbstractMessageTransformer {

	public static Logger log = Logger.getLogger(MarshalObjectTransformer.class);

	private String contentType = null;
	private String viewName = null;
	protected Class<?> view;

	public Class<?> getView() {
		return view;
	}

	public void setView(Class<?> view) {
		this.view = view;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) throws ClassNotFoundException {
		this.viewName = viewName;
		if (!StringUtils.isEmpty(viewName))
			view = ClassUtils.getClass(viewName);
	}
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// allow flow to override what is generated
		String useContentType = null;
		if (contentType != null) {
			useContentType = contentType;
		} else {
			useContentType = message.getProperty("blaze.responseContentType", PropertyScope.INVOCATION);
			if (useContentType == null)
				useContentType = message.getInboundProperty("Accept");
		}
		log.trace("Using Content-Type: " + useContentType);
		outputEncoding = "UTF-8";

		try {
			if (useContentType == null)
				useContentType = "application/json";

			message.setOutboundProperty("Content-Type", useContentType);
			return firstRecognizedType(message, outputEncoding, useContentType);
		} catch (Exception e) {
			e.printStackTrace();
			message.setOutboundProperty("MarshalObjectErrorMsg", e.toString());
			throw new TransformerException(MessageFactory.createStaticMessage("Marshal Exception: " + e.toString()));
		}
	}

	private Object firstRecognizedType(MuleMessage message, String outputEncoding, String mimeTypes) throws Exception {
		StringTokenizer tok = new StringTokenizer(mimeTypes, ", ");
		while (tok.hasMoreElements()) {
			String mtype = tok.nextToken();
			if (mtype.equalsIgnoreCase("application/json")) {
				return transformJSON(message, outputEncoding);
			} else if (mtype.equalsIgnoreCase("application/xml")) {
				return transformXML(message, outputEncoding);
			}
		}
		return transformJSON(message, outputEncoding);
	}

	private Object transformJSON(MuleMessage message, String outputEncoding)
			throws Exception {
		Object obj = message.getPayload();
		return doTransformJSON(obj);
	}

	public Object doTransformJSON(Object payload)
			throws Exception {
		log.trace("transformJSON");
		ObjectMapper mapper = new ObjectMapper();
		//mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		if (view != null)
			return mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION).writerWithView(view).writeValueAsString(payload);
		else
			return mapper.writeValueAsString(payload);
	}

	private Object transformXML(MuleMessage message, String outputEncoding)
			throws Exception {
		Object obj = message.getPayload();
		return doTransformXML(obj.getClass(), obj);
	}

	public Object doTransformXML(Class<?> iClass, Object payload) throws Exception {
		log.trace("transformXML");
		StringWriter writer = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(iClass);
			Marshaller marshaller = context.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.marshal(payload, writer);
			return writer.toString();
		} finally {
			if (writer != null) {
				IOUtils.closeQuietly(writer);
			}
		}
	}

}
