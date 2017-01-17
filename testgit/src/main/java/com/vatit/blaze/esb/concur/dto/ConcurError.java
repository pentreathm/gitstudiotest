package com.vatit.blaze.esb.concur.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.vatit.blaze.esb.concur.ConcurException;
import com.vatit.blaze.utils.ToThrowable;

@XmlRootElement(name="Error")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonRootName(value = "Error")
public class ConcurError implements ToThrowable {
	
	@JsonProperty("Message")
	@XmlElement(name="Message")
	private String message;
	@JsonProperty("Server-Time")
	@XmlElement(name="Server-Time")
	private Date serverTime;
	@JsonProperty("Id")
	@XmlElement(name="Id")
	private String id;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getServerTime() {
		return serverTime;
	}
	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Throwable toThrowable() {
		return new ConcurException(String.format("%s at %s [ID: %s]", getMessage(), getServerTime().toString(), getId()));
	}
	
}
