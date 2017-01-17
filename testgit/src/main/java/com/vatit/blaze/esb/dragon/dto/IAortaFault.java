package com.vatit.blaze.esb.dragon.dto;

public interface IAortaFault {
	public Integer getFaultcode();
	public String getFaultstring();
	public String getRuncode();
	public FaultDetail getDetail();
}
