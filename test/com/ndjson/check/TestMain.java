package com.ndjson.check;

import com.sanderswangbin.pull.api.PullObj;

public class TestMain {
	final static String NDJSON_EXAMPLE_01_TRUE = "{\"items\": [{\"operation\": "
			+ "{\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP\"}}, "
			+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate\"}}, "
			+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_report\"}}, "
			+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"groups\", \"type\": \"create\", \"id\": \"HTTP2\"}}, "
			+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"metrics\", \"type\": \"create\", \"id\": \"HTTP.response.error.rate.2\"}}, "
			+ "{\"operation\": {\"status\": 201, \"successful\": true, \"endpoint\": \"reports\", \"type\": \"create\", \"id\": \"web_2_report\"}}], "
			+ "\"errors\": false}";
	final static String REG_EXAMPLE_01 = "\"operation\":\\s*\\{"
            + "\\s*\"status\":\\s*%s,[a-zA-Z0-9:,\\s\"]*"
            + "\"endpoint\":\\s*\"%s\",[a-zA-Z0-9:,\\s\"]*"
            + "\"type\":\\s*\"%s\",[a-zA-Z0-9:,\\s\"]*"
            + "\"id\":\\s*\"%s*\".*";
	final static String[][] CHECK_EXAMPLE_01= {{"201", "groups", "create", "HTTP"},
			{"201", "metrics", "create", "HTTP.response.error.rate"},
			{"201", "reports", "create", "web_report"},
			{"201", "groups", "create", "HTTP2"},
			{"201", "metrics", "create", "HTTP.response.error.rate.2"},
			{"201", "reports", "create", "web_2_report"}};

	final static String PULL_EXAMPLE_01 = "r\'\\{\"operation\":\\s*\\{"
	        + "\\s*\"status\": ([0-9]*),[a-zA-Z0-9:,\\s\"]*"
			+ "\"endpoint\": \"([a-zA-Z]*)\",[a-zA-Z0-9:,\\s\"]*"
	        + "\"type\": \"([a-zA-Z]*)\",[a-zA-Z0-9:,\\s\"]*"
			+ "\"id\": \"([a-zA-Z0-9\\._-]*)\"\\}\'"
			+ ".PULL("
			+ "{0}==201;"
			+ "{2}==\"create\";"
			+ "{1}==[\"groups\",\"metrics\",                 \"reports\",   \"groups\",\"metrics\",                   \"reports\"];"
			+ "{3}==[\"HTTP\",  \"HTTP.response.error.rate\",\"web_report\",\"HTTP2\", \"HTTP.response.error.rate.2\",\"web_2_report\"])";

	public static void main(String... argv) {
		// NDJSON check using regex.
		NdjsonCheckTestRaw nck = new NdjsonCheckTestRaw();
		String regex = new String();
		for (Integer i = 0; i < CHECK_EXAMPLE_01.length; i++) {
			regex = regex + String.format(REG_EXAMPLE_01, 
					CHECK_EXAMPLE_01[i][0],
					CHECK_EXAMPLE_01[i][1],
					CHECK_EXAMPLE_01[i][2],
					CHECK_EXAMPLE_01[i][3]);
		}
		System.out.println("Check result using regex: " + nck.checkUseRegex(regex, NDJSON_EXAMPLE_01_TRUE));
		
		// NDJSON check using PULL.
		try {
		    PullObj p = new PullObj(PULL_EXAMPLE_01);
		    System.out.println("Check result using PULL: " + p.check(NDJSON_EXAMPLE_01_TRUE).getResult());
		    // Print out the debug info.
		    System.out.println(p);
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}
