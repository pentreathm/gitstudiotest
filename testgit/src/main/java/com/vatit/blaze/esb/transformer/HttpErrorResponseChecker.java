package com.vatit.blaze.esb.transformer;

import java.io.StringReader;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.apache.commons.httpclient.HttpStatus;

import com.vatit.blaze.dto.BlazeError;
import com.vatit.blaze.esb.concur.dto.ConcurError;
import com.vatit.blaze.esb.exception.BlazeEsbException;
import com.vatit.blaze.esb.exception.CodeNotImplementedException;
import com.vatit.blaze.esb.exception.ExternalSiteIsDownException;
import com.vatit.blaze.esb.exception.HttpResponseException;
import com.vatit.blaze.esb.exception.HttpResponseTransformerException;
import com.vatit.blaze.esb.exception.UnhandledErrorPayloadException;
import com.vatit.blaze.utils.MimeTypes;
import com.vatit.blaze.utils.ToThrowable;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpErrorResponseChecker extends AbstractMessageTransformer {

	private enum RequestTarget {
		BLAZE_SERVICES("Blaze Services"), 
		CONCUR_EXPENSE("Concur Expense", "www.concursolutions.com"), 
		CONCUR_IS_DOWN("Concur Expense", "sitedown.concursolutions.com"), 
		UNKNOWN("Unrecognised Endpoint");

		private String prettyName;
		private String pattern = null;
			
		RequestTarget(String prettyName) {
			this.prettyName = prettyName;
		}
		
		RequestTarget(String prettyName, String pattern) {
			this(prettyName);
			this.pattern = pattern;
		}
		
		public String getPrettyName() {
			return prettyName;
		}
		
		public boolean matches(String s) {
			if (pattern == null)
				return false;
			else
				return s.contains(pattern);
		}
	}
	
	private RequestTarget uriToTarget(String uri) {
		for (RequestTarget target : RequestTarget.values())
			if (target.matches(uri))
				return target;
			
		if (uri.contains(_blazeServicesAddress)) 
			return RequestTarget.BLAZE_SERVICES;
		else
			return RequestTarget.UNKNOWN;
	}
	
	protected String _blazeServicesAddress;
	
  public String getBlazeServicesAddress()
  {
        return _blazeServicesAddress;
  }
  public void setBlazeServicesAddress(String blazeAddress)
  {
  	_blazeServicesAddress = blazeAddress;
  }	
  
  protected Boolean _surfaceHttpStatus;
  
	public Boolean getSurfaceHttpStatus() {
		return _surfaceHttpStatus;
	}
	public void setSurfaceHttpStatus(Boolean _surfaceHttpStatus) {
		this._surfaceHttpStatus = _surfaceHttpStatus;
	}
	private static final String[] JSON_XML = {MimeTypes.MIME_APPLICATION_JSON, MimeTypes.MIME_APPLICATION_XML};  
	
	private static final String[] BAD_REDIRECT_SITES = {"http://sitedown.concursolutions.com/"};
	
	@SuppressWarnings("unchecked")
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String requestUri = message.getInboundProperty("http.request");
		Integer statusCode = getHttpStatus(message);
		Boolean isBadSite = Arrays.asList(BAD_REDIRECT_SITES).contains(requestUri);
		// Prevent favicon requests - they come from browser testing
		if ((statusCode >= HttpStatus.SC_BAD_REQUEST || isBadSite) && !requestUri.contains("favicon.ico")) {
			RequestTarget target = uriToTarget(requestUri);
			
			String contentType = message.getInboundProperty("content-type");
			if (contentType != null)
				contentType = contentType.split(";")[0];
			// Resolve error payload contents
			// TODO: how to make these checked - can we get rid of the suppression? (speak to Java expert)
			@SuppressWarnings("rawtypes")
			Class errorClass = null;
			boolean unwrapRoot = false;
			switch (target) {
				case BLAZE_SERVICES:
					if (Arrays.asList(JSON_XML).contains(contentType)) 
						errorClass = BlazeError.class; 
					break;
				case CONCUR_EXPENSE:
					if (Arrays.asList(JSON_XML).contains(contentType)) 
						errorClass = ConcurError.class;
					unwrapRoot = true;
					break;
				default: 
			}
			
			String payload;
			try {
				try {
					payload = message.getPayloadAsString();
				
					if (errorClass != null) {
						if (ToThrowable.class.isAssignableFrom(errorClass)) {
							ToThrowable error;
							if (MimeTypes.MIME_APPLICATION_JSON.equals(contentType)) {
								ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, unwrapRoot);
								error = (ToThrowable) mapper.readValue(payload, errorClass);
							} else if (MimeTypes.MIME_APPLICATION_XML.equals(contentType)) {
								JAXBContext jaxbContext = JAXBContext.newInstance(errorClass);
								Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

								StringReader reader = new StringReader(payload);
								error = (ToThrowable)unmarshaller.unmarshal(reader);
							} else {
								throw new BlazeEsbException(String.format("Unsupported content-type %s for %s error object unmarshal", contentType, target.getPrettyName())); 
							}
							throw error.toThrowable(); 
						} else {
							throw new CodeNotImplementedException(String.format("Class %s should implement %s Interface.", errorClass.getName(), ToThrowable.class.getName()));
						}
					} else {
						if (target == RequestTarget.CONCUR_IS_DOWN)
							throw new ExternalSiteIsDownException(target.getPrettyName(), payload);
						else
							throw new UnhandledErrorPayloadException(payload);   
					}
					
				} catch (Throwable e) {
					throw new HttpResponseException(statusCode, e); 
				}
			} catch (Throwable e) {
				String exceptionMessage = target == RequestTarget.UNKNOWN ? "Call failed [%s]" : String.format("Call to %s failed [%%s]", target.getPrettyName());
				exceptionMessage = String.format(exceptionMessage, requestUri);
				throw new HttpResponseTransformerException(this, statusCode, exceptionMessage, getSurfaceHttpStatus(), e);
			}
		}
		
		return message.getPayload();
	}

	protected int getHttpStatus(MuleMessage message) {
		return Integer.parseInt((String) message.getInboundProperty("http.status"));
	}

}
