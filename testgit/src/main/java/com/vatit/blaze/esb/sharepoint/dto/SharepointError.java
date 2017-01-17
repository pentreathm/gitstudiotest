package com.vatit.blaze.esb.sharepoint.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "error")
public class SharepointError {
	
	private String code;
	
	public class Message {
		private String lang;
		private String value;
		public String getLang() {
			return lang;
		}
		public void setLang(String lang) {
			this.lang = lang;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	private Message message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
