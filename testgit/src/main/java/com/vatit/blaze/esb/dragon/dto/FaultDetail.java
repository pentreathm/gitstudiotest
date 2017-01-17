package com.vatit.blaze.esb.dragon.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="detail")
public class FaultDetail {
	public FaultDetail(){
		errors = new FaultErrors();
	}
	@XmlElement
	public FaultError error;

	@XmlElement
	public FaultErrors errors;
}
