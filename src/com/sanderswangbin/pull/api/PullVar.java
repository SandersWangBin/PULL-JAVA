package com.sanderswangbin.pull.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sanderswangbin.pull.util.Operator;

public class PullVar {
	private String REG_PULL_VAR = "\\{([0-9]+)\\}\\s*([!<=>]+)\\s*(.*)";
	private String QUOTE_SINGLE = "\'";
	private String QUOTE_DOUBLE = "\"";
	private String TYPE_STRING = "STRING";
	private String TYPE_INTEGER = "INTEGER";

	private Integer index = -1;
	private String alias = "";
	private String op = "";
	private String value = "";
	private String expect = "";
	private boolean result = false;
	private String type = TYPE_STRING;

	public PullVar(String pullArg) {
		Matcher m = Pattern.compile(REG_PULL_VAR).matcher(pullArg);
		if (m.find()) {
			this.index = Integer.valueOf(m.group(1));
			this.op = m.group(2);
			this.expect = m.group(3).trim();
			if (startsAndEndsWith(expect, QUOTE_SINGLE) || startsAndEndsWith(expect, QUOTE_DOUBLE)) {
				type = TYPE_STRING;
				expect = removeSubText(expect, 1);
			} else {
				type = TYPE_INTEGER;
			}
		}
	}

	public Integer getIndex() {
		return this.index;
	}

	public boolean getResult() {
		checkResult();
		return this.result;
	}

	public PullVar setValue(String value) {
		this.value = value;
		return this;
	}

	public void clean() {
		value = "";
		result = false;
	}

	private boolean startsAndEndsWith(String text, String subText) {
		return text.startsWith(subText) && text.endsWith(subText);
	}

	private String removeSubText(String text, int subTextLength) {
		if (text != null && text.length() > subTextLength*2) {
			return text.substring(subTextLength, text.length()-subTextLength);
		} else {
			return text;
		}
	}

	private void checkResult() {
		if (this.type == TYPE_INTEGER) {
			this.result = compare(Integer.valueOf(this.value), this.op, Integer.valueOf(this.expect));
		} else {
			this.result = compare(this.value, this.op, this.expect);
		}
	}

	public boolean compare(int left, String op, int right) {
		try {
		    return Operator.cmp.get(op).compare(left, right);
		} catch (Exception e){
			return false;
		}
	}

	public boolean compare(String left, String op, String right) {
		try {
		    return Operator.cmp.get(op).compare(left, right);
		} catch (Exception e) {
			return false;
		}
	}
}
