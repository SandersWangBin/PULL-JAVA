package com.json.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCheck {
	final static String JSON_EXAMPLE_01 = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
	final static String PULL_EXAMPLE_01 = "p\'\\{\"message\": \"Created.*\'([a-zA-Z0-9_-]+)\'\"\\}\'.PULL()";
	final static String REG_EXAMPLE_01 = "\\{\"message\": \"Created.*\'([a-zA-Z0-9_-]+)\'\"\\}";

	public static void main(String... argv) {
		Pattern p = Pattern.compile(REG_EXAMPLE_01);
		Matcher m = p.matcher(JSON_EXAMPLE_01);
		if (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
	}
}
