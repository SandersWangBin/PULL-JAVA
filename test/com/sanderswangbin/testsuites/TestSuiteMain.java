package com.sanderswangbin.testsuites;

import com.sanderswangbin.testcases.jsonck.TestCaseJsonCK;
import com.sanderswangbin.testcases.pull.TestCasePullChain;
import com.sanderswangbin.testcases.pull.TestCasePullRegexp;

public class TestSuiteMain {

	public static void main(String... argv) {
		System.out.println(new TestCasePullRegexp().test());
		System.out.println(new TestCasePullChain().test());
		System.out.println(new TestCaseJsonCK().test());
	}

}
