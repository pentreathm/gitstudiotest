package com.vatit.blaze.esb.dragon.dto;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="Fault")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fault implements IAortaFault {
	@XmlElement
	private Integer faultcode;
	@XmlElement
	private String faultstring;
	@XmlElement
	private String runcode;
	@XmlElement
	private FaultDetail detail;

	@Override
	public Integer getFaultcode() {
		return faultcode;
	}
	public void setFaultcode(Integer faultcode) {
		this.faultcode = faultcode;
	}
	@Override
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	@Override
	public String getRuncode() {
		return runcode;
	}
	public void setRuncode(String runcode) {
		this.runcode = runcode;
	}
	@Override
	public FaultDetail getDetail() {
		return detail;
	}
	public void setDetail(FaultDetail detail) {
		this.detail = detail;
	}
}
