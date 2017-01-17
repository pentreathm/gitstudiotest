package com.vatit.blaze.esb.dragon.dto;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="errors")
public class FaultErrors {
	public FaultErrors(){
		errors = new ArrayList<String>();
	}
	
	@XmlElement(name="error")
	public ArrayList<String> errors;
}