package com.vatit.blaze.esb.utils;

public class JsonDejunker {

	public static String dejunk(String json) {
		String dejunkedJson = json.substring(json.indexOf('{'));
		return dejunkedJson;
	}
}
