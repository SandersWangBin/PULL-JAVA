package com.sanderswangbin.jsonck;

import java.util.ArrayList;
import java.util.List;

import com.sanderswangbin.pull.PullChain;
import com.sanderswangbin.pull.PullObj;

public class JsonCK {
	private final static String SEPERATOR_OR = "\\|\\|";
	private final static String SEPERATOR_AND = "&&";
	private final static String SYMBOL_NEWLINE = "\n";
	private final static String SYMBOL_OR = "||";
	private final static String SYMBOL_AND = "&&";

	private final static String FORMAT_JSONCK_EXP_NAME = "JSONCK_EXP_%s";
	private static String JSONCK_EXP_ALL = "ALL";
	private static Integer JSONCK_EXP_IND = 0;

	private String pullExp = "";
	private List<JsonCKExp> jsonCKExpList = new ArrayList<JsonCKExp>();
	private boolean result = false;

	public JsonCK(String jsonCKExpLine) {
		genJsonCKExpList(jsonCKExpLine);
		pullExp = genPullExp();
	}

	private void genJsonCKExpList(String jsonCKExpLine) {
		for (String subLine : jsonCKExpLine.split(SEPERATOR_AND)) {
			String op = SYMBOL_AND;
			for (String exp : subLine.split(SEPERATOR_OR)) {
				jsonCKExpList.add(
						new JsonCKExp(String.format(FORMAT_JSONCK_EXP_NAME, JSONCK_EXP_IND.toString()), op, exp));
				op = SYMBOL_OR;
				JSONCK_EXP_IND++;
			}
			op = SYMBOL_AND;
		}
	}

	private String genPullExpRef() {
		return String.format(FORMAT_JSONCK_EXP_NAME, JSONCK_EXP_ALL);
	}

	private String genPullExp() {
		String result = "m\'";
		for (JsonCKExp jExp : jsonCKExpList) {
			result += jExp.pullExp() + SYMBOL_NEWLINE;
		}
		result += genPullExpRef() + ": ";
		for (Integer i = 0; i < JSONCK_EXP_IND; i++) {
			result += String.format(FORMAT_JSONCK_EXP_NAME, i.toString()) + " + ";
		}
		if (result.substring(result.length()-3, result.length()).contains(" + ")) {
			result = result.substring(0, result.length()-3);
		}
		result += "\'.PULL(" + genPullExpRef() + ")";
		return result;
	}

	private boolean calculateBoolean(boolean first, String op, boolean second) {
		if (op.contains(SYMBOL_AND)) {
			return first && second;
		} else if (op.contains(SYMBOL_OR)) {
			return first || second;
		} else {
			return false;
		}
	}

	private String getOpFromExpList(String expName) {
		for (JsonCKExp exp : jsonCKExpList) {
			if (exp.name().contains(expName)) return exp.operator();
		}
		return null;
	}

	public JsonCK check(String text) {
		try {
			boolean localResult = true;
		    PullChain pChain = new PullChain(pullExp);
		    for (PullObj obj : pChain.check(text).pullChainCurrent().children()) {
		    	localResult = calculateBoolean(localResult, getOpFromExpList(obj.name()), obj.result());
		    }
		    this.result = localResult;
		    //System.out.println(pChain);
		} catch (Exception e) {
			//System.out.println(e);
		}
		
		return this;
	}

	public boolean result() {
		return this.result;
	}

	public String toString() {
		String result = "==== JSONCK INFO ====\n";
		result += this.pullExp + "\n";
		return result;
	}
}
