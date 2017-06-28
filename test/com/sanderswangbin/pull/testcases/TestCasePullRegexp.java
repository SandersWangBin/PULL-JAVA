package com.sanderswangbin.pull.testcases;

import com.sanderswangbin.pull.PullObj;

public class TestCasePullRegexp {
	private final static String TC_DETAIL_INDENT = "    ";
	private final static String TC_NEWLINE = "\n";

	private static String checkUsePull(String tcName, String pullExp, String text, boolean expect) {
		String result = "TestCasePullRegexp: " + tcName + TC_NEWLINE;
		try {
			boolean r = new PullObj(pullExp).check(text).result();
			result += TC_DETAIL_INDENT + "PULL REGEXP EXPRESSION: " + pullExp + TC_NEWLINE;
			result += TC_DETAIL_INDENT + "CHECKED TEXT          : " + text + TC_NEWLINE;
			result += TC_DETAIL_INDENT + "JAVA CODE STYPLE      : new PullObj(\"" + pullExp + "\").check(\"" + text + "\").result()" + TC_NEWLINE;
			result += TC_DETAIL_INDENT + "PULL EXECUTE RESULT   : " + r + TC_NEWLINE;
			result += TC_DETAIL_INDENT + "====> TEST CASE RESULT: " + (expect == r) + TC_NEWLINE + TC_NEWLINE;
		} catch (Exception e) {
			result += TC_DETAIL_INDENT + e + TC_NEWLINE + TC_NEWLINE;
		}
		return result;
	}

	public static String test() {
		String result = "";

		String TC_01_NAME = "PULL REGEXP EXPRESSION TEST 01";
		String PULL_EXAMPLE_01 = "r\'\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}\'.PULL({0}==\"Created\";{1}==\"ExampleObj01\")";
		String JSON_EXAMPLE_01_TRUE = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
		String JSON_EXAMPLE_01_FALSE = "{\"message\": \"Failed to created \'ExampleObj01\'\"}";
		result += checkUsePull(TC_01_NAME, PULL_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, true);
		result += checkUsePull(TC_01_NAME, PULL_EXAMPLE_01, JSON_EXAMPLE_01_FALSE, false);

		String TC_02_NAME = "PULL REGEXP EXPRESSION TEST 02";
		String PULL_EXAMPLE_02 = "r\'\\{\"ID\":\\s*([0-9]+).*\"NAME\":\\s*(.*)\\s*\\}\'.PULL({0}==10;{1}<>\"OBJ[0-9]+\")";
		String JSON_EXAMPLE_02_TRUE = "{\"ID\": 10, \"NAME\": \"OBJ010\"}"; 
		result += checkUsePull(TC_02_NAME, PULL_EXAMPLE_02, JSON_EXAMPLE_02_TRUE, true);

		String TC_03_NAME = "PULL REGEXP EXPRESSION TEST 03";
		String PULL_EXAMPLE_03 = "r\'\\{\"ID\":\\s*([0-9]+)\\s*,\\s*\"NAME\":\\s*\"([A-Z0-9]+)\"\\s*\\}\'.PULL({0}==[10,15];{1}==[\"OBJ010\",\"OBJ015\"])";
		String JSON_EXAMPLE_03_TRUE  = "[{\"ID\": 10, \"NAME\": \"OBJ010\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
		String JSON_EXAMPLE_03_FALSE = "[{\"ID\": 11, \"NAME\": \"OBJ011\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
		result += checkUsePull(TC_03_NAME, PULL_EXAMPLE_03, JSON_EXAMPLE_03_TRUE, true);
		result += checkUsePull(TC_03_NAME, PULL_EXAMPLE_03, JSON_EXAMPLE_03_FALSE, false);

		return result;

	}
}
