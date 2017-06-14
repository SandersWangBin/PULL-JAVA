package com.sanderswangbin.pull.util;

public interface OperatorCmpItf<T extends Comparable<T>> {
	boolean compare(T left, T right);
}
