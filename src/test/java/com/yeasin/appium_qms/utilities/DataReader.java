package com.yeasin.appium_qms.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataReader {
	public static JSONObject getJsonData() throws IOException, ParseException {
		// pass the path of test_data.json file
		File filename = new File(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\test_data\\test_data.json");
		// convert the json file into string
		String json = FileUtils.readFileToString(filename, "UTF-8");
		// parse the string into object
		Object obj = new JSONParser().parse(json);
		// convert the object into json object
		JSONObject jsonObject = (JSONObject) obj;
		
		return jsonObject;
	}
	
	public static CharSequence getTestData(String key) throws IOException, ParseException {
		// get the value from the key
		String val = (String) getJsonData().get(key);
		return val;
	}

	public static int getIntTestData(String key) throws IOException, ParseException {
		int val = (int) getJsonData().get(key);
		return val;
	}
	/*public static long getLongTestData(String key) throws IOException, ParseException {
		long val = (long) getJsonData().get(key);
		return val;
	}*/

	public static long getLongTestData(String key) throws IOException, ParseException {
		Object value = getJsonData().get(key); // Get the value from JSON

		if (value instanceof Number) {
			return ((Number) value).longValue(); // Convert directly if it's a Number
		} else {
			return Long.parseLong(value.toString()); // Convert from String if needed
		}
	}
}
