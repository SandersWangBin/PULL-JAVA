package com.sanderswangbin.pull.util;

import java.util.HashSet;

public class OperatorFactory {
	private static HashSet<String> cmpSet = new HashSet<String>();
	private static HashSet<String> regSet = new HashSet<String>();
	
	static {
		cmpSet.add(">");
		cmpSet.add(">=");
		cmpSet.add("==");
		cmpSet.add("!=");
		cmpSet.add("<");
		cmpSet.add("<=");
		regSet.add("<>");
		regSet.add("><");
	}

	public static boolean compare(String left, String op, String right) {
		try {
			if (cmpSet.contains(op)) {
		        return OperatorCmp.cmp.get(op).compare(left, right);
			} else if (regSet.contains(op)) {
				return OperatorReg.reg.get(op).compare(left,  right);
			} else {
				return false;
			}
		} catch (Exception e){
			return false;
		}
	}

	public static boolean compare(Integer left, String op, Integer right) {
		try {
			if (cmpSet.contains(op)) {
		        return OperatorCmp.cmp.get(op).compare(left, right);
			} else {
				return false;
			}
		} catch (Exception e){
			return false;
		}
	}
}
