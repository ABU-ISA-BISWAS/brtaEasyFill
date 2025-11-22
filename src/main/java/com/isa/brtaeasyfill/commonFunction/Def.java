package com.isa.brtaeasyfill.commonFunction;

import org.json.JSONObject;

public class Def {
	
	/**
	 * 
	 * @param json
	 * @param key
	 * @return String
	 */
	public static String getString(JSONObject json, String key) {
		if (json.has(key)) {
			if (json.get(key) != null && json.get(key) != "" &&  !json.get(key).equals("{}")) {
				return json.get(key).toString();
			} else {
				return null;
			}
		}
		return null;
	}


}
