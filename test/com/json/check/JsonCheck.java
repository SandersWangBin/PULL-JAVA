package com.json.check;

import com.sanderswangbin.pull.api.PullObj;
import com.sanderswangbin.pull.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCheck {
	final static String JSON_EXAMPLE_01_TRUE = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
	final static String JSON_EXAMPLE_01_FALSE = "{\"message\": \"Failed to created \'ExampleObj01\'\"}";
	final static String PULL_EXAMPLE_01 = "p\'\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}\'.PULL({0}==\"Created\";{1}==\"ExampleObj01\")";
	final static String REG_EXAMPLE_01 = "\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}";
	final static String[] REG_EXAMPLE_01_CHECKS = {"Created", "ExampleObj01"};

	final static String JSON_EXAMPLE_02_TRUE = "{\"ID\": 10, \"NAME\": \"OBJ010\"}"; 
	final static String PULL_EXAMPLE_02 = "p\'\\{\"ID\":\\s*([0-9]+).*\"NAME\":\\s*(.*)\\s*\\}\'.PULL({0}==10;{1}<>\"OBJ[0-9]+\")";

	final static String JSON_EXAMPLE_03_TRUE  = "[{\"ID\": 10, \"NAME\": \"OBJ010\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
	final static String JSON_EXAMPLE_03_FALSE = "[{\"ID\": 11, \"NAME\": \"OBJ011\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
	final static String PULL_EXAMPLE_03 = "p\'\\{\"ID\":\\s*([0-9]+)\\s*,\\s*\"NAME\":\\s*\"([A-Z0-9]+)\"\\s*\\}\'.PULL({0}==[10,15];{1}==[\"OBJ010\",\"OBJ015\"])";
	
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

	public List<Boolean> checkUsePull(String pullExp, String... texts) {
		List<Boolean> results = new ArrayList<Boolean>();
		try {
		    PullObj p = new PullObj(pullExp);
		    for (String text : texts) {
		        results.add(p.check(text).getResult());
		    }
		} catch (Exception e) {
			System.out.print(e);
		}
		return results;
	}

	public static void main(String... argv) {
		JsonCheck jck = new JsonCheck();
		System.out.println("Check result using regex: " + jck.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, REG_EXAMPLE_01_CHECKS));
		System.out.println("Check result using regex: " + jck.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_FALSE, REG_EXAMPLE_01_CHECKS));

		for (Boolean result : jck.checkUsePull(PULL_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, JSON_EXAMPLE_01_FALSE)) {
			System.out.println("Check result using PULL (EXAMPLE_01): " + result);
		}

		for (Boolean result : jck.checkUsePull(PULL_EXAMPLE_02, JSON_EXAMPLE_02_TRUE)) {
			System.out.println("Check result using PULL (EXAMPLE_02): " + result);
		}

		for (Boolean result : jck.checkUsePull(PULL_EXAMPLE_03, JSON_EXAMPLE_03_FALSE, JSON_EXAMPLE_03_TRUE)) {
			System.out.println("Check result using PULL (EXAMPLE_03): " + result);
		}
	}
}
