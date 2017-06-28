package com.sanderswangbin.testcases.pull;

import com.sanderswangbin.pull.PullObj;
import com.sanderswangbin.testcases.base.TestCaseBase;

public class TestCasePullRegexp extends TestCaseBase {
	public TestCasePullRegexp() {
		super("TestCasePullRegexp");
	}

	private void checkUsePull(String tcName, String pullExp, String text, boolean expect) {
		try {
			boolean r = new PullObj(pullExp).check(text).result();
			genResult(tcName, 
					genDescription(
							"PULL REGEXP EXPRESSION: " + pullExp,
							"CHECKED TEXT          : " + text,
							"JAVA CODE STYPLE      : new PullObj(\"" + pullExp + "\").check(\"" + text + "\").result();",
							"PULL EXECUTE RESULT   : " + r), 
					(r==expect));
		} catch (Exception e) {
			genResult(tcName, e.toString(), false);
		}
	}

	@Override
	public TestCasePullRegexp test() {
		String TC_01_NAME = "PULL REGEXP EXPRESSION TEST 01";
		String PULL_EXAMPLE_01 = "r\'\\{\"message\": \"([a-zA-Z]+)\\s.*\'([a-zA-Z0-9_-]+)\'\"\\}\'.PULL({0}==\"Created\";{1}==\"ExampleObj01\")";
		String JSON_EXAMPLE_01_TRUE = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
		String JSON_EXAMPLE_01_FALSE = "{\"message\": \"Failed to created \'ExampleObj01\'\"}";
		checkUsePull(TC_01_NAME, PULL_EXAMPLE_01, JSON_EXAMPLE_01_TRUE, true);
		checkUsePull(TC_01_NAME, PULL_EXAMPLE_01, JSON_EXAMPLE_01_FALSE, false);

		String TC_02_NAME = "PULL REGEXP EXPRESSION TEST 02";
		String PULL_EXAMPLE_02 = "r\'\\{\"ID\":\\s*([0-9]+).*\"NAME\":\\s*(.*)\\s*\\}\'.PULL({0}==10;{1}<>\"OBJ[0-9]+\")";
		String JSON_EXAMPLE_02_TRUE = "{\"ID\": 10, \"NAME\": \"OBJ010\"}"; 
		checkUsePull(TC_02_NAME, PULL_EXAMPLE_02, JSON_EXAMPLE_02_TRUE, true);

		String TC_03_NAME = "PULL REGEXP EXPRESSION TEST 03";
		String PULL_EXAMPLE_03 = "r\'\\{\"ID\":\\s*([0-9]+)\\s*,\\s*\"NAME\":\\s*\"([A-Z0-9]+)\"\\s*\\}\'.PULL({0}==[10,15];{1}==[\"OBJ010\",\"OBJ015\"])";
		String JSON_EXAMPLE_03_TRUE  = "[{\"ID\": 10, \"NAME\": \"OBJ010\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
		String JSON_EXAMPLE_03_FALSE = "[{\"ID\": 11, \"NAME\": \"OBJ011\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
		checkUsePull(TC_03_NAME, PULL_EXAMPLE_03, JSON_EXAMPLE_03_TRUE, true);
		checkUsePull(TC_03_NAME, PULL_EXAMPLE_03, JSON_EXAMPLE_03_FALSE, false);

		String TC_04_NAME = "PULL REGEXP EXPRESSION TEST 04, complicated PULL expression";
		String PULL_EXAMPLE_04 = "r\'\\{\"operation\":\\s*\\{"
		        + "\\s*\"status\": ([0-9]*),[a-zA-Z0-9:,\\s\"]*"
				+ "\"endpoint\": \"([a-zA-Z]*)\",[a-zA-Z0-9:,\\s\"]*"
		        + "\"type\": \"([a-zA-Z]*)\",[a-zA-Z0-9:,\\s\"]*"
				+ "\"id\": \"([a-zA-Z0-9\\._-]*)\"\\}\'"
				+ ".PULL("
				+ "{0}==201;"
				+ "{2}==\"create\";"
				+ "{1}==[\"groups\",\"metrics\",                 \"reports\",   \"groups\",\"metrics\",                   \"reports\"];"
				+ "{3}==[\"HTTP\",  \"HTTP.response.error.rate\",\"web_report\",\"HTTP2\", \"HTTP.response.error.rate.2\",\"web_2_report\"])";
		String JSON_EXAMPLE_04_TRUE = "{\"items\": [{\"operation\": "
				+ "{\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_report\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP2\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate.2\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_2_report\"}}], "
				+ "\"errors\": false}";
		String JSON_EXAMPLE_04_FALSE = "{\"items\": [{\"operation\": "
				+ "{\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_report\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP2\"}}, "
				+ "{\"operation\": {\"status\": 400, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate.2\"}}, "
				+ "{\"operation\": {\"status\": 501, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_2_report\"}}], "
				+ "\"errors\": true}";
		checkUsePull(TC_04_NAME, PULL_EXAMPLE_04, JSON_EXAMPLE_04_TRUE, true);
		checkUsePull(TC_04_NAME, PULL_EXAMPLE_04, JSON_EXAMPLE_04_FALSE, false);

		return this;
	}
}
