package com.ndjson.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NdjsonCheckTestRaw {
	public boolean checkUseRegex(String regex, String text) {
		boolean result = false;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			result = true;
		}
		return result;
	}
}
