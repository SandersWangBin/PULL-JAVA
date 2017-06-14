package com.sanderswangbin.pull.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OperatorReg {
	public static Map<String, OperatorRegItf> reg = new HashMap<String, OperatorRegItf>();

	static {
		reg.put("<>", new OperatorRegItf() {
			@Override public boolean compare(String left, String right) {
				return Pattern.compile(right).matcher(left).find();
			}
		});
		reg.put("><", new OperatorRegItf() {
			@Override public boolean compare(String left, String right) {
				return !(Pattern.compile(right).matcher(left).find());
			}
		});
	}
}
