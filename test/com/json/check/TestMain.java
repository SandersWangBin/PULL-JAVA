package com.json.check;

import com.sanderswangbin.jsonck.JsonCK;

public class TestMain {
	final static String JSON_EXAMPLE_01_TRUE = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
	final static String JSON_EXAMPLE_01_FALSE = "{\"message\": \"Failed to created \'ExampleObj01\'\"}";
	final static String PULL_EXAMPLE_01 = "r\'\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}\'.PULL({0}==\"Created\";{1}==\"ExampleObj01\")";
	final static String REG_EXAMPLE_01 = "\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}";
	final static String[] REG_EXAMPLE_01_CHECKS = {"Created", "ExampleObj01"};

	final static String JSON_EXAMPLE_02_TRUE = "{\"ID\": 10, \"NAME\": \"OBJ010\"}"; 
	final static String PULL_EXAMPLE_02 = "r\'\\{\"ID\":\\s*([0-9]+).*\"NAME\":\\s*(.*)\\s*\\}\'.PULL({0}==10;{1}<>\"OBJ[0-9]+\")";

	final static String JSON_EXAMPLE_03_TRUE  = "[{\"ID\": 10, \"NAME\": \"OBJ010\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
	final static String JSON_EXAMPLE_03_FALSE = "[{\"ID\": 11, \"NAME\": \"OBJ011\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
	final static String PULL_EXAMPLE_03 = "r\'\\{\"ID\":\\s*([0-9]+)\\s*,\\s*\"NAME\":\\s*\"([A-Z0-9]+)\"\\s*\\}\'.PULL({0}==[10,15];{1}==[\"OBJ010\",\"OBJ015\"])";

	final static String PULL_CHAIN_01 = "f\'test\\com\\json\\check\\Rule_JsonCK_01.pull\'.PULL(PULL_EXAMPLE_01)";
	final static String PULL_CHAIN_02 = "f\'test\\com\\json\\check\\Rule_JsonCK_01.pull\'"
			+ ".PULL(PULL_EXAMPLE_02_01 + PULL_EXAMPLE_02_02)";
	final static String PULL_CHAIN_03 = "f\'test\\com\\json\\check\\Rule_JsonCK_01.pull\'"
			+ ".PULL(PULL_EXAMPLE_03)";

	private static void testRegex() {
		JsonCheckTestRaw jckRaw = new JsonCheckTestRaw();
		System.out.println("Check result using regex: " + jckRaw.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, REG_EXAMPLE_01_CHECKS));
		System.out.println("Check result using regex: " + jckRaw.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_FALSE, REG_EXAMPLE_01_CHECKS));
	}

	private static void testPullRaw() {
		JsonCheckTestRaw jckRaw = new JsonCheckTestRaw();
		for (Boolean result : jckRaw.checkUsePull(PULL_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, JSON_EXAMPLE_01_FALSE)) {
			System.out.println("Check result using PULL Raw (EXAMPLE_01): " + result);
		}

		for (Boolean result : jckRaw.checkUsePull(PULL_EXAMPLE_02, JSON_EXAMPLE_02_TRUE)) {
			System.out.println("Check result using PULL Raw (EXAMPLE_02): " + result);
		}

		for (Boolean result : jckRaw.checkUsePull(PULL_EXAMPLE_03, JSON_EXAMPLE_03_FALSE, JSON_EXAMPLE_03_TRUE)) {
			System.out.println("Check result using PULL Raw (EXAMPLE_03): " + result);
		}
	}

	private static void testPullChain() {
		JsonCheckTestRaw jckRaw = new JsonCheckTestRaw();
		for (Boolean result : jckRaw.checkUsePullChain(PULL_CHAIN_01, JSON_EXAMPLE_01_TRUE, JSON_EXAMPLE_01_FALSE)) {
			System.out.println("Check result using PULL Chain (EXAMPLE_01): " + result);
		}

		for (Boolean result: jckRaw.checkUsePullChain(PULL_CHAIN_02, JSON_EXAMPLE_02_TRUE)) {
			System.out.println("Check result using PULL Chain (EXAMPLE_02): " + result);
		}

		for (Boolean result: jckRaw.checkUsePullChain(PULL_CHAIN_03, JSON_EXAMPLE_03_FALSE, JSON_EXAMPLE_03_TRUE)) {
			System.out.println("Check result using PULL Chain (EXAMPLE_03): " + result);
		}
	}

	public static void main(String... argv) {
		// JSON check using regular expression
		testRegex();

		// JSON check using raw Pull expression
		testPullRaw();

		// JSON check using Pull chain expression
		testPullChain();

		// JSON check using JsonCK expression
		JsonCK jck = new JsonCK("\"message\"==\"Created\" || \"id\"==\"HTTP\" || \"result\"==true");
		jck.check("{\"message\": \"Created\"}");
		jck.check("{\"result\": true}");
	}
}
