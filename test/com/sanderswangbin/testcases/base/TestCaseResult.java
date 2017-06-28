package com.sanderswangbin.testcases.base;

public class TestCaseResult {
	private boolean status = false;
	private String description = "";

	public TestCaseResult status(boolean status) {
		this.status = status;
		return this;
	}

	public TestCaseResult description(String description) {
		this.description = description;
		return this;
	}

	public boolean status() {
		return this.status;
	}

	public String description() {
		return this.description;
	}
}
