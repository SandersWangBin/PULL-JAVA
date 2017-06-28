package com.sanderswangbin.pull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PullChain {
	private final static String REG_PULL_CHAIN_FILE_EXP = "f\'(.*)\'.PULL\\((.*)\\)";
	private final static String REG_PULL_CHAIN_MULTILINES_EXP = "m\'(.*)\'.PULL\\((.*)\\)";

	private final static String REG_PULL_OBJ_EXP = "^([a-zA-Z0-9\\._-]+)\\s*:\\s*(.*)";

	private final static String SEPERATOR_NEWLINE = "\n";
	private final static String SYMBOL_NEXT = ">";
	private final static String SYMBOL_PLUS = "\\+";

	private String pullChainFile = null;
	private String pullChainMultilines = null;
	private String pullChain = null;
	private PullCtrl pullChainRoot = null;
	private PullCtrl pullChainCurrent = null;
	private Map<String, PullObj> pullObjs = new HashMap<String, PullObj>();
	private Map<String, String> pullObjRefs = new HashMap<String, String>();
	private boolean result = false;

	public PullChain(String pullExp) throws Exception {
		Matcher m = Pattern.compile(REG_PULL_CHAIN_FILE_EXP).matcher(pullExp);
		if (m.find()) {
			this.pullChainFile = m.group(1);
			readPullChainFile(this.pullChainFile);
			this.pullChain = genPullChains(m.group(2));
		} else {
			m = Pattern.compile(REG_PULL_CHAIN_MULTILINES_EXP, Pattern.DOTALL).matcher(pullExp);
			if (m.find()) {
				this.pullChainMultilines = m.group(1);
				readPullChainMultilines(this.pullChainMultilines);
				this.pullChain = genPullChains(m.group(2));
			} else {
				throw new Exception("ERROR: wrong PULL expression.");
			}
		}
	}

	private void readPullChainFile(String fileName) throws IOException {
		// Open the file
		FileInputStream fstream = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			readPullChainLine(strLine);
		}
		//Close the input stream
		br.close();
	}

	private void readPullChainMultilines(String multilines) {
		for (String strLine : multilines.split(SEPERATOR_NEWLINE)) {
			readPullChainLine(strLine);
		}
	}

	private void readPullChainLine(String line) {
		try {
			Matcher m = Pattern.compile(REG_PULL_OBJ_EXP).matcher(line);
			if (m.find()) {
				if (PullObj.test(m.group(2))) {
					pullObjs.put(m.group(1), new PullObj(m.group(1), m.group(2)));
				} else {
					pullObjRefs.put(m.group(1), m.group(2));
				}
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	private String updateUsedPullChainName(String pullChainString) {
		if (pullObjRefs.get(pullChainString) != null) return pullObjRefs.get(pullChainString);
		else return pullChainString;
	}

	private String genPullChains(String pullChainString) {
		PullCtrl previous = null;
		this.pullChain = updateUsedPullChainName(pullChainString);
		for (String l : this.pullChain.split(SYMBOL_NEXT)) {
			PullCtrl ctrl = new PullCtrl();
			if (this.pullChainRoot == null) this.pullChainRoot = ctrl;
			if (previous != null) previous.next(ctrl);
			previous = ctrl;
			for (String m : l.split(SYMBOL_PLUS)) {
				if (this.pullObjs.get(m.trim()) != null) ctrl.children().add(this.pullObjs.get(m.trim()));
			}
		}
		this.pullChainCurrent = this.pullChainRoot;
		return this.pullChain;
	}

	private boolean checkLine(PullCtrl ctrl, String line) {
		boolean result = false;
		if (ctrl != null && line.length() > 0) {
			for (PullObj obj : ctrl.children()) {
				boolean r = obj.check(line).result();
				result = result || r;
			}
		}
		return result;
	}

	public PullChain check(String text) {
		this.result = checkLine(this.pullChainCurrent, text);
		if (this.result == false && this.pullChainCurrent.next() != null) {
			this.result = checkLine(this.pullChainCurrent.next(), text);
			if (this.result == true) this.pullChainCurrent = this.pullChainCurrent.next();
		}
		return this;
	}

	public PullCtrl pullChainCurrent() {
		return this.pullChainCurrent;
	}

	public boolean result() {
		return this.result;
	}

	public String toString() {
		String result = "==== Pull Chain ====\n";
		if (pullChainFile != null)       result += "Pull Chain File      : " + this.pullChainFile + "\n";
		if (pullChainMultilines != null) result += "Pull Chain MultiLines: " + this.pullChainMultilines + "\n";
		if (pullChain != null)           result += "Pull Chain           : " + this.pullChain + "\n";
		if (pullChainRoot != null)       result += "Pull Chain Root      : " + toStringChainRoot() + "\n";
		result += "Pull Chain Result: " + this.result + "\n";
		result += "Pull Objs: \n";
		for (String key : this.pullObjs.keySet()) {
			result += this.pullObjs.get(key);
		}
		result += "Pull Obj Refs: \n";
		for (String key : this.pullObjRefs.keySet()) {
			result += "  key: " + key + " -> " + this.pullObjRefs.get(key) + "\n";
		}
		return result;
	}

	private String toStringChainRoot() {
		String result = "";
		if (this.pullChainRoot != null) {
			PullCtrl next = this.pullChainRoot;
			result += next + " > ";
		}
		if (result.length() >= 3) result = result.substring(0, result.length()-3);
		return result;
	}
}
