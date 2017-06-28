package com.sanderswangbin.pull;

import java.util.ArrayList;
import java.util.List;

public class PullCtrl {
	private List<PullObj> children = new ArrayList<PullObj>();
	private PullCtrl next = null;

	public List<PullObj> children() {
		return this.children;
	}


	public void next(PullCtrl next) {
		this.next = next;
	}

	public PullCtrl next() {
		return this.next;
	}

	public String toString() {
		String result = "(";
		for (int i = 0; i < children.size(); i++) {
			result += children.get(i).name() + " + ";
		}
		if (result.length() >= 3) result = result.substring(0, result.length()-3);
		return result += ")";
	}
}
