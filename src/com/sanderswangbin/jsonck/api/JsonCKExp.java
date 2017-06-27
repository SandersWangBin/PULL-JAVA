package com.sanderswangbin.jsonck.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCKExp {
	private final static String REG_JSONCK_EXPRESS = "\\s*(\".*\")\\s*([!<=>]+)\\s*(.*)\\s*";
	private final static String REG_JSONCK_VAR_TYPE_INTEGER = "[0-9]+";

	private final static String FORMAT_PULL_EXP_STRING  = "%s: r\'%s\\s*:\\s*\"([a-zA-Z0-9\\._-]+)\"\\s*\'.PULL({0}%s)";
	private final static String FORMAT_PULL_EXP_INTEGER = "%s: r\'%s\\s*:\\s*([0-9]+)\\s*\'.PULL({0}%s)";

	private final static String TYPE_STRING = "STRING";
	private final static String TYPE_INTEGER = "INTEGER";

	private String name = "";
	private String operator = "";
	private String expression = "";
	private String expVar = "";
	private String expOp = "";
	private String expValue = "";
	private String expType = "";
	private String pullExp = "";

	public JsonCKExp(String name, String operator, String expression) {
		this.name = name;
		this.operator = operator.trim();
		this.expression = expression.trim();
		parserJsonCKExp(this.expression);
		this.pullExp = genPullExp();
	}

	private String getType(String var) {
		if (Pattern.compile(REG_JSONCK_VAR_TYPE_INTEGER).matcher(var).find()) {
			return TYPE_INTEGER;
		} else {
			return TYPE_STRING;
		}
	}

	private void parserJsonCKExp(String expression) {
		Matcher m = Pattern.compile(REG_JSONCK_EXPRESS).matcher(expression);
		if (m.find()) {
			expVar = m.group(1);
			expOp = m.group(2);
			expValue = m.group(3);
			expType = getType(expVar);
		}
	}

	private String genPullExp() {
		if (expType == TYPE_STRING) return String.format(FORMAT_PULL_EXP_STRING, name, expVar, expOp+expValue);
		else if (expType == TYPE_INTEGER) return String.format(FORMAT_PULL_EXP_INTEGER, name, expVar, expOp+expValue);
		else return "";
	}

	public String pullExp() {
		return this.pullExp;
	}

	public String toString() {
		String result = "==== JSONCK EXPRESSION INFO ====\n";
		result += "operator  : " + operator + "\n";
//		result += "expression: " + expression + "\n";
		result += "PULL exp  : " + pullExp + "\n";
		return result;
	}
}