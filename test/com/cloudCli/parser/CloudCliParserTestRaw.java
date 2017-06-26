package com.cloudCli.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudCliParserTestRaw {

	public List<String> parserUseRegex(String regex, String text) {
		List<String> result = new ArrayList<String>(); 
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				result.add(m.group(i));
			}
		}
		return result;
	}

}
