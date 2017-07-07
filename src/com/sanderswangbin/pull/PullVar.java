package com.sanderswangbin.pull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sanderswangbin.pull.util.OperatorFactory;

public class PullVar {
	private final static String REG_PULL_VAR = "\\{([0-9]+)\\}\\s*([!<=>]+)\\s*(.*)";
	private final static String REG_TYPE_STRING = "^[\'\"](.*)[\'\"]$";
//	private final static String REG_TYPE_STRING_2 = "[a-zA-Z]+";
	private final static String REG_TYPE_INTEGER = "^[0-9\\.\\,]+$";
	private final static String REG_TYPE_LIST = "^\\[(.*)\\]$";

	private final static String SEPERATE_COMMA = ",";
	private final static String TYPE_STRING = "STRING";
	private final static String TYPE_INTEGER = "INTEGER";

	private Integer index = -1;
	private String alias = "";
	private String op = "";
	private List<String> values = new ArrayList<String>();
	private List<String> expects = new ArrayList<String>();
	private boolean result = false;
	private String type = TYPE_STRING;
//	private static Integer value_index = -1;

	public PullVar(String pullArg) throws Exception {
		Matcher m = Pattern.compile(REG_PULL_VAR).matcher(pullArg);
		if (m.find()) {
			this.index = Integer.valueOf(m.group(1));
			this.op = m.group(2);
			genExpects(m.group(3).trim());
		} else {
			throw new Exception("ERROR: wrong PULL variable expression.");
		}
	}

	public Integer index() {
		return this.index;
	}

	public boolean result() {
		return checkResult();
	}

	public PullVar value(String value) {
		addValue(value);
		return this;
	}

	public List<String> values() {
		return this.values;
	}

	public String alias() {
		return this.alias;
	}

	public void clean() {
		result = false;
		this.values.clear();
//		value_index = -1;
	}

	private boolean checkResult() {
		if (this.expects.size() == 0 || this.values.size() == 0) {
			return this.result;
		}
		boolean localResult = true;
		for (Integer i = 0; i < this.values.size(); i++) {
			if (this.type == TYPE_INTEGER) {
				localResult = localResult && OperatorFactory.compare(Integer.valueOf(safeGet(this.values, i)), this.op, Integer.valueOf(safeGet(this.expects, i)));
			} else {
				localResult = localResult &&  OperatorFactory.compare(safeGet(this.values, i), this.op, safeGet(this.expects, i));
			}
		}
		this.result = localResult;
		return this.result;
	}

	private void genExpects(String expectExpress) {
		expectExpress = expectExpress.trim();
		Matcher m = Pattern.compile(REG_TYPE_LIST).matcher(expectExpress);
		if (m.find()) {
			expectExpress = m.group(1);
			for (String e : expectExpress.split(SEPERATE_COMMA)) addExpect(e);
		} else {
			addExpect(expectExpress);
		}
	}

	private void addExpect(String expect) {
		expect = expect.trim();
		if (Pattern.compile(REG_TYPE_INTEGER).matcher(expect).find()) {
			this.type = TYPE_INTEGER;
		}
		else {
			this.type = TYPE_STRING;
			Matcher m = Pattern.compile(REG_TYPE_STRING).matcher(expect);
			if (m.find()) expect = m.group(1).trim();
		}
		if (expect.length() > 0) {
			this.expects.add(expect);
		}
	}

	private void addValue(String value) {
		this.values.add(value);
	}

	private String safeGet(List<String> list, Integer index) {
		if (index < list.size()) {
			return list.get(index);
		} else {
			return list.get(list.size() - 1);
		}
	}

	public String toString() {
		return (new String ("---- PullVar Debug Info ----\n" +
		"    - index   : " + index + "\n" +
		"    - alias   : " + alias + "\n" +
		"    - operator: " + op + "\n" +
		"    - values  : " + toStringList(values) + "\n" +
		"    - expects : " + toStringList(expects) + "\n" +
		"    - result  : " + result + "\n" +
		"    - type    : " + type + "\n"));
	}

	private String toStringList(List<String> list) {
		String valuesString = new String();
		for (int i = 0; i < list.size(); i++) {
			valuesString = valuesString + list.get(i) + "; "; 
		}
		return valuesString;
	}
}