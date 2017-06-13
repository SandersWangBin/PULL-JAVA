package com.sanderswangbin.pull.api;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PullObj {
	private final static String REG_PULL_EXP = "p\'(.*)\'.PULL\\((.*)\\)";

	private String regExp = null;
	private Map<Integer, PullVar> pullArgs = new HashMap<Integer, PullVar>();

	public PullObj(String pullExp) throws Exception {
		Matcher m = Pattern.compile(REG_PULL_EXP).matcher(pullExp);
		if (m.find()) {
			this.regExp = m.group(1);
			for (String a : m.group(2).split(";")) {
				PullVar v = new PullVar(a.trim());
				pullArgs.put(v.getIndex(), v);
			}
		} else {
			throw new Exception("ERROR: wrong PULL expression.");
		}
	}

	public boolean check(String text) {
		boolean result = true;
		Matcher m = Pattern.compile(this.regExp).matcher(text);
		if (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				PullVar v = (PullVar)this.pullArgs.get(i-1);
				if (v != null) {
					result = result && v.setValue(m.group(i)).getResult();
				}
			}
		} else {
			result = false;
		}
		return result;
	}
}
