package com.sanderswangbin.pull.api;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PullObj {
	private final static String REG_PULL_EXP = "p\'(.*)\'.PULL\\((.*)\\)";

	private String regExp = null;
	private Map<Integer, PullVar> pullVars = new HashMap<Integer, PullVar>();
	private boolean result = false;

	public PullObj(String pullExp) throws Exception {
		Matcher m = Pattern.compile(REG_PULL_EXP).matcher(pullExp);
		if (m.find()) {
			this.regExp = m.group(1);
			parserPullVars(m.group(2));
		} else {
			throw new Exception("ERROR: wrong PULL expression.");
		}
	}

	public PullObj check(String text) {
		cleanPullVars();
		Matcher m = Pattern.compile(this.regExp).matcher(text);
		while (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				PullVar v = (PullVar)this.pullVars.get(i-1);
				if (v != null) {
					v.value(m.group(i)).result();
				}
			}
		}
		this.result = resultPullVars();
		return this;
	}

	public boolean getResult() {
		return this.result;
	}

	public Map<Integer, PullVar> getVars() {
		return this.pullVars;
	}

	private void cleanPullVars() {
		for (Integer key : this.pullVars.keySet()) {
			this.pullVars.get(key).clean();
		}
	}

	private void parserPullVars(String argvExp) {
		for (String a : argvExp.split(";")) {
			try {
			    PullVar v = new PullVar(a.trim());
			    this.pullVars.put(v.index(), v);
			} catch (Exception e) {
				//TO DO: handle the special operator
			}
		}
	}

	private boolean resultPullVars() {
		boolean localResult = true;
		for (Integer key : this.pullVars.keySet()) {
			localResult = localResult && this.pullVars.get(key).result();
		}
		return localResult;
	}

	public String toString() {
		String resultString = new String();
		resultString = "==== PullObj Debug Info ====\n"
		        + "= regExp: " + regExp + "\n" 
				+ "= PullVars: \n";
		for (Integer key : this.pullVars.keySet()) {
			resultString = resultString + "Key: " + key + "\n" + this.pullVars.get(key);
		}
		resultString = resultString + "= result: " + result + "\n\n";
		return resultString;
	}
}
