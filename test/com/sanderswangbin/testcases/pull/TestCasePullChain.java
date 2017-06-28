package com.sanderswangbin.testcases.pull;


import com.sanderswangbin.pull.PullChain;
import com.sanderswangbin.testcases.base.TestCaseBase;

public class TestCasePullChain extends TestCaseBase {
	public TestCasePullChain() {
		super("TestCasePullChain");
	}

	private void checkUsePullChain(String tcName, String pullChainExp, String text, boolean expect) {
		try {
			boolean r = new PullChain(pullChainExp).check(text).result();
			genResult(tcName, 
					genDescription(
							"PULL CHAIN EXPRESSION : " + pullChainExp,
							"CHECKED TEXT          : " + text,
							"JAVA CODE STYPLE      : new PullChain(\"" + pullChainExp + "\").check(\"" + text + "\").result();",
							"PULL EXECUTE RESULT   : " + r), 
					(r==expect));
		} catch (Exception e) {
			genResult(tcName, e.toString(), false);
		}
	}

	@Override
	public TestCasePullChain test() {
		String TC_01_NAME = "PULL CHAIN EXPRESSION TEST 01";
		String PULL_CHAIN_01 = "f\'test\\com\\sanderswangbin\\testcases\\pull\\Rule_JsonCK_01.pull\'.PULL(PULL_EXAMPLE_01)";
		String JSON_EXAMPLE_01_TRUE = "{\"message\": \"Created example object \'ExampleObj01\'\"}";
		String JSON_EXAMPLE_01_FALSE = "{\"message\": \"Failed to created \'ExampleObj01\'\"}";
		checkUsePullChain(TC_01_NAME, PULL_CHAIN_01, JSON_EXAMPLE_01_TRUE, true);
		checkUsePullChain(TC_01_NAME, PULL_CHAIN_01, JSON_EXAMPLE_01_FALSE, false);

		String TC_02_NAME = "PULL CHAIN EXPRESSION TEST 02";
		String PULL_CHAIN_02 = "f\'test\\com\\sanderswangbin\\testcases\\pull\\Rule_JsonCK_01.pull\'"
				+ ".PULL(PULL_EXAMPLE_02_01 + PULL_EXAMPLE_02_02)";
		String JSON_EXAMPLE_02_TRUE = "{\"ID\": 10, \"NAME\": \"OBJ010\"}"; 
		checkUsePullChain(TC_02_NAME, PULL_CHAIN_02, JSON_EXAMPLE_02_TRUE, true);

		String TC_03_NAME = "PULL CHAIN EXPRESSION TEST 03";
		String PULL_CHAIN_03 = "f\'test\\com\\sanderswangbin\\testcases\\pull\\Rule_JsonCK_01.pull\'"
				+ ".PULL(PULL_EXAMPLE_03)";
		String JSON_EXAMPLE_03_TRUE  = "[{\"ID\": 10, \"NAME\": \"OBJ010\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
		String JSON_EXAMPLE_03_FALSE = "[{\"ID\": 11, \"NAME\": \"OBJ011\"}, {\"ID\": 15, \"NAME\": \"OBJ015\"}]";
		checkUsePullChain(TC_03_NAME, PULL_CHAIN_03, JSON_EXAMPLE_03_TRUE, true);
		checkUsePullChain(TC_03_NAME, PULL_CHAIN_03, JSON_EXAMPLE_03_FALSE, false);

		return this;
	}
}
