package com.sanderswangbin.pull.util;

public interface OperatorItf<T extends Comparable<T>> {
	boolean compare(T left, T right);
}
