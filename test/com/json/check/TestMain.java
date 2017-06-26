package com.json.check;

import com.sanderswangbin.pull.api.PullChain;

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

	final static String PULL_CHAIN = "f\'test\\com\\json\\check\\Rule_JsonCK_01.pull\'.PULL(PULL_EXAMPLE_01)";

	public static void main(String... argv) {
		// JSON check using regular expression
		JsonCheckTestRaw jckRaw = new JsonCheckTestRaw();
		System.out.println("Check result using regex: " + jckRaw.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, REG_EXAMPLE_01_CHECKS));
		System.out.println("Check result using regex: " + jckRaw.checkUseRegex(REG_EXAMPLE_01, JSON_EXAMPLE_01_FALSE, REG_EXAMPLE_01_CHECKS));

		// JSON check using raw Pull expression
		for (Boolean result : jckRaw.checkUsePull(PULL_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, JSON_EXAMPLE_01_FALSE)) {
			System.out.println("Check result using PULL (EXAMPLE_01): " + result);
		}

		for (Boolean result : jckRaw.checkUsePull(PULL_EXAMPLE_02, JSON_EXAMPLE_02_TRUE)) {
			System.out.println("Check result using PULL (EXAMPLE_02): " + result);
		}

		for (Boolean result : jckRaw.checkUsePull(PULL_EXAMPLE_03, JSON_EXAMPLE_03_FALSE, JSON_EXAMPLE_03_TRUE)) {
			System.out.println("Check result using PULL (EXAMPLE_03): " + result);
		}

		// JSON check using Pull chain expression
		try {
		    PullChain pChain = new PullChain(PULL_CHAIN);
		    System.out.println(pChain);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
