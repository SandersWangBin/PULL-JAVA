package com.sanderswangbin.pull.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OperatorCmp {
	public static Map<String, OperatorCmpItf> cmp = new HashMap<String, OperatorCmpItf>();

	static {
		cmp.put(">", new OperatorCmpItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)>0);
			}
		});
		cmp.put(">=", new OperatorCmpItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)>=0);
			}
		});
		cmp.put("==", new OperatorCmpItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)==0);
			}
		});
		cmp.put("!=", new OperatorCmpItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)!=0);
			}
		});
		cmp.put("<", new OperatorCmpItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)<=0);
			}
		});
		cmp.put("<", new OperatorCmpItf() {
			@Override public boolean compare(Comparable left, Comparable right) {
				return (left.compareTo(right)<0);
			}
		});
	}
}
