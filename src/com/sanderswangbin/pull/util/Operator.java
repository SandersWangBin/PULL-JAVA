package com.sanderswangbin.pull.util;

import java.util.HashMap;
import java.util.Map;

public class Operator {
	Map<String, OperatorItf> cmp = new HashMap<String, OperatorItf>();

	public <T> Operator() {
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

	public OperatorItf get(String op) {
		return cmp.get(op);
	}
}
