package com.sanderswangbin.testcases.pull;

public class TestSuiteMain {

	public static void main(String... argv) {
		System.out.println(new TestCasePullRegexp().test());
		System.out.println(new TestCasePullChain().test());
	}

}
