package com.sanderswangbin.testcases.jsonck;

import com.sanderswangbin.jsonck.JsonCK;
import com.sanderswangbin.pull.PullObj;
import com.sanderswangbin.testcases.base.TestCaseBase;

public class TestCaseJsonCK extends TestCaseBase {
	public TestCaseJsonCK() {
		super("TestCaseJsonCK");
	}

	private void checkUseJsonCK(String tcName, String jsonCKExp, String text, boolean expect) {
		try {
			boolean r = new JsonCK(jsonCKExp).check(text).result();
			genResult(tcName, 
					genDescription(
							"JSONCK EXPRESSION     : " + jsonCKExp,
							"CHECKED TEXT          : " + text,
							"JAVA CODE STYPLE      : new JsonCK(\"" + jsonCKExp + "\").check(\"" + text + "\").result();",
							"PULL EXECUTE RESULT   : " + r), 
					(r==expect));
		} catch (Exception e) {
			genResult(tcName, e.toString(), false);
		}
	}

	@Override
	public TestCaseJsonCK test() {
		String TC_00_NAME = "JSONCK EXPRESSION TEST 00";
		String JSONCK_EXP_00 = "\"method\"==\"Created\"";
		String JSONCK_EXAMPLE_00_01 = "{\"id\": \"HTTP\", \"method\": \"Created\", \"error\": false}";
		checkUseJsonCK(TC_00_NAME, JSONCK_EXP_00, JSONCK_EXAMPLE_00_01, true);

		String TC_01_NAME = "JSONCK EXPRESSION TEST 01";
		String JSONCK_EXP_01 = "\"method\"==\"Created\" || \"id\"==\"HTTP\" || \"result\"==true";
		String JSONCK_EXAMPLE_01_01 = "{\"method\": \"Created\"}";
		String JSONCK_EXAMPLE_01_02 = "{\"result\": true}";
		String JSONCK_EXAMPLE_01_03 = "{\"successful\": true}";
		checkUseJsonCK(TC_01_NAME, JSONCK_EXP_01, JSONCK_EXAMPLE_01_01, true);
		checkUseJsonCK(TC_01_NAME, JSONCK_EXP_01, JSONCK_EXAMPLE_01_02, true);
		checkUseJsonCK(TC_01_NAME, JSONCK_EXP_01, JSONCK_EXAMPLE_01_03, false);

		String TC_02_NAME = "JSONCK EXPRESSION TEST 02";
		String JSONCK_EXP_02 = "\"method\"==\"Created\" && \"id\"==\"HTTP\" || \"result\"==true";
		String JSONCK_EXAMPLE_02_01 = "{\"id\": \"HTTP\", \"method\": \"Created\", \"error\": false}";
		String JSONCK_EXAMPLE_02_02 = "{\"id\": \"HTTP\", \"method\": \"Updated\", \"error\": true}";
		checkUseJsonCK(TC_02_NAME, JSONCK_EXP_02, JSONCK_EXAMPLE_02_01, true);
		checkUseJsonCK(TC_02_NAME, JSONCK_EXP_02, JSONCK_EXAMPLE_02_02, false);

		String TC_03_NAME = "JSONCK EXPRESSION TEST 03";
		String JSONCK_EXP_03 = "\"error\"==[true, false] && \"ID\"==[10, 15] && \"NAME\"==[\"OBJ010\", \"OBJ015\"]";
		String JSONCK_EXAMPLE_03_01  = "[{\"ID\": 10, \"NAME\": \"OBJ010\", \"error\": true}, {\"ID\": 15, \"NAME\": \"OBJ015\", \"error\": false}]";
		checkUseJsonCK(TC_03_NAME, JSONCK_EXP_03, JSONCK_EXAMPLE_03_01, true);

		String TC_04_NAME = "JSONCK EXPRESSION TEST 04";
		String JSONCK_EXP_04 = "\"status\"==201 "
				+ "&& \"id\"==[\"HTTP\",\"HTTP.response.error.rate\",\"web_report\"] "
				+ "&& \"endpoint\"==[\"groups\",\"metrics\",\"reports\"] "
				+ "&& \"successful\"==true";
		String JSONCK_EXAMPLE_04_01 = "{\"items\": ["
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate\"}}, "
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_report\"}}"
				+ "], \"errors\": false}";
		String JSONCK_EXAMPLE_04_02 = "{\"items\": ["
				+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP\"}}, "
				+ "{\"operation\": {\"status\": 404, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate\"}}, "
				+ "{\"operation\": {\"status\": 500, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_report\"}}"
				+ "], \"errors\": false}";
		checkUseJsonCK(TC_04_NAME, JSONCK_EXP_04, JSONCK_EXAMPLE_04_01, true);
		checkUseJsonCK(TC_04_NAME, JSONCK_EXP_04, JSONCK_EXAMPLE_04_02, false);

		return this;
	}

}
