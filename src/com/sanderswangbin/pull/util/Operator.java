package com.sanderswangbin.pull.util;

import java.util.HashMap;
import java.util.Map;

public class Operator {
	public static Map<String, OperatorItf> cmp = new HashMap<String, OperatorItf>();

	static {
		cmp.put(">", new OperatorItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)>0);
			}
		});
		cmp.put(">=", new OperatorItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)>=0);
			}
		});
		cmp.put("==", new OperatorItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)==0);
			}
		});
		cmp.put("!=", new OperatorItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)!=0);
			}
		});
		cmp.put("<", new OperatorItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)<=0);
			}
		});
		cmp.put("<", new OperatorItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)<0);
			}
		});
	}

}
