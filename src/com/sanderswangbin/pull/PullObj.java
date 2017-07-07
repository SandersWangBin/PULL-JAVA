package com.sanderswangbin.pull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PullObj {
	private final static String REG_PULL_OBJ_EXP = "r\'(.*)\'.PULL\\((.*)\\)";

	private String pullObjName = "";
	private String regExp = null;
	private Map<Integer, PullVar> pullVars = new HashMap<Integer, PullVar>();
	private boolean result = false;
	private boolean match = false;

	public static boolean test(String pullExp) {
		Matcher m = Pattern.compile(REG_PULL_OBJ_EXP).matcher(pullExp);
		return m.find();
	}

	public PullObj(String pullExp) throws Exception {
		initPullObj(pullExp);
	}

	public PullObj(String objName, String pullExp) throws Exception {
		this.pullObjName = objName;
		initPullObj(pullExp);
	}

	private void initPullObj(String pullExp) throws Exception {
		Matcher m = Pattern.compile(REG_PULL_OBJ_EXP).matcher(pullExp);
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
			this.match = true;
			for (int i = 1; i <=m.groupCount(); i++) {
				PullVar v = (PullVar)this.pullVars.get(i-1);
				if (v != null) {
					v.value(m.group(i));
				}
			}
		}
		this.result = resultPullVars();
		return this;
	}

	public boolean result() {
		return this.result;
	}

	public boolean match() {
		return this.match;
	}

	public Map<Integer, PullVar> vars() {
		return this.pullVars;
	}

	public String name() {
		return this.pullObjName;
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
				+ "= objName : " + pullObjName + "\n"
		        + "= regExp  : " + regExp + "\n" 
		        + "= matched : " + match + "\n"
				+ "= PullVars: \n";
		for (Integer key : this.pullVars.keySet()) {
			resultString = resultString + "Key: " + key + "\n" + this.pullVars.get(key);
		}
		resultString = resultString + "= result: " + result + "\n\n";
		return resultString;
	}
}
