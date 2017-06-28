package com.sanderswangbin.testcases.base;

import java.util.ArrayList;
import java.util.List;

public abstract class TestCaseBase {
	private final static String TC_DETAIL_INDENT = "    ";
	private final static String TC_NEWLINE = "\n";
	private final static String TC_HEADER = "> TESTCASE: %s" + TC_NEWLINE;
	private final static String TC_SUM = "==== TEST SUITE (%s) RESULT: SUCCESS (%d/%d => %s%%), FAIL (%d/%d)" + TC_NEWLINE;

	private List<TestCaseResult> results = new ArrayList<TestCaseResult>();
	private String name = "";

	protected TestCaseBase(String name) {
		this.name = name;
	}

	protected void genResult(String tcName, String description, boolean status) {
		results.add(new TestCaseResult()
				.description(String.format(TC_HEADER, tcName) + description + 
						TC_DETAIL_INDENT + "====> TEST CASE RESULT: " + status + TC_NEWLINE + TC_NEWLINE)
				.status(status));
	}

	protected String genDescription(String... texts) {
		String result = "";
		for (String text : texts) {
			result += TC_DETAIL_INDENT + text + TC_NEWLINE;
		}
		return result;
	}

	private String toStringSum() {
		int success = 0;
		int fail = 0;
		for (TestCaseResult r : results) {
			if (r.status()) success++;
			else fail++;
		}
		return String.format(TC_SUM, 
				name,
				success, results.size(), new Double((double)success/(double)results.size()*(double)100.0).toString(), 
				fail, results.size());
	}

	private String toStringDetails() {
		String resultString = "";
		for (TestCaseResult r : results) {
			resultString += r.description();
		}
		return resultString;
	}

	public String toString() {
		return toStringSum() + toStringDetails();
	}

	public abstract TestCaseBase test();
}
