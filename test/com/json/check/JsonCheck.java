package com.json.check;

import com.sanderswangbin.pull.api.PullObj;
import com.sanderswangbin.pull.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCheck {
	final static String JSON_EXAMPLE_01_TRUE = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
	final static String JSON_EXAMPLE_01_FALSE = "{\"message\": \"Failed to created \'ExampleObj01\'\"}";
	final static String PULL_EXAMPLE_01 = "p\'\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}\'.PULL({0}==\"Created\";{1}==\"ExampleObj01\")";
	final static String REG_EXAMPLE_01 = "\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}";
	final static String[] REG_EXAMPLE_01_CHECKS = {"Created", "ExampleObj01"};

	public boolean checkUseRegex(String regex, String text, String[] checks) {
		boolean result = true;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				result = result && (checks[i-1].equals(m.group(i)));
			}
		} else {
			result = false;
		}
		return result;
	}

	public boolean checkUsePull(String pullEx, String text) {
		try {
		    PullObj p = new PullObj(pullEx);
		    return p.check(text);
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String... argv) {
		JsonCheck jck = new JsonCheck();
		System.out.println("Check result using regex: " + jck.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, REG_EXAMPLE_01_CHECKS));
		System.out.println("Check result using regex: " + jck.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_FALSE, REG_EXAMPLE_01_CHECKS));
		
		System.out.println("Check result using PULL: " + jck.checkUsePull(PULL_EXAMPLE_01, JSON_EXAMPLE_01_TRUE));
		System.out.println("Check result using PULL: " + jck.checkUsePull(PULL_EXAMPLE_01, JSON_EXAMPLE_01_FALSE));
	}
}
