package com.vatit.blaze.esb.dragon.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="error")
public class FaultError {
	@XmlAttribute
	public Integer code;
	
	@XmlValue
	public String textValue;
}
