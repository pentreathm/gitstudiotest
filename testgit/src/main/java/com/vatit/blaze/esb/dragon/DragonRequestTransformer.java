package com.vatit.blaze.esb.dragon;

import com.vatit.blaze.esb.exception.SimpleTransformerException;
import com.vatit.wyvern.shared.dto.exception.WSCallException;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import java.lang.reflect.InvocationTargetException;

public class DragonRequestTransformer extends AbstractMessageTransformer {

	private String proxyClass;
	private String methodName;
	private Boolean hasPayload = true;
	private Boolean useServiceAccount = false;
	private DragonRequestor requestor;
	
	@Override
	public void setMuleContext(MuleContext context) {
		super.setMuleContext(context);
		try {
			requestor = context.getRegistry().lookupObject(DragonRequestor.class);
		} catch (RegistrationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		try {
			return requestor.call(message, proxyClass, methodName, hasPayload, useServiceAccount);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			throw new SimpleTransformerException(this, String.format("Unable to create proxy class: %s and call method: %s", proxyClass, methodName), e);
		} catch(InvocationTargetException e){
			throw new SimpleTransformerException(this, String.format("Error encountered while calling method: %s from proxy class: %s", methodName, proxyClass), e.getTargetException());	
		} catch(WSCallException e) {
			throw new SimpleTransformerException(this, String.format("Unable to login to the system with a services account while calling method: %s from proxy class %s",methodName, proxyClass), e);
		}
	}

	public String getProxyClass() {
		return proxyClass;
	}

	public void setProxyClass(String proxyClass) {
		this.proxyClass = proxyClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Boolean getHasPayload() {
		return hasPayload;
	}

	public void setHasPayload(Boolean hasPayload) {
		this.hasPayload = hasPayload;
	}
	
	public Boolean getUseServiceAccount() {
		return useServiceAccount;
	}

	public void setUseServiceAccount(Boolean useServiceAccount) {
		this.useServiceAccount = useServiceAccount;
	}

}
