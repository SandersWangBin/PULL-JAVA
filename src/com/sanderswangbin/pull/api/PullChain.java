package com.sanderswangbin.pull.api;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PullChain {
	private final static String REG_PULL_CHAIN_EXP = "f\'(.*)\'.PULL\\((.*)\\)";
	private final static String REG_PULL_OBJ_EXP = "^([a-zA-Z0-9\\._-]+)\\s*:\\s*(.*)";
	private String pullChainFile = null;
	private String pullChain = null;
	private Map<String, PullObj> pullObjs = new HashMap<String, PullObj>(); 

	public PullChain(String pullExp) throws Exception {
		Matcher m = Pattern.compile(REG_PULL_CHAIN_EXP).matcher(pullExp);
		if (m.find()) {
			this.pullChainFile = m.group(1);
			this.pullChain = m.group(2);
			readPullChainFile(this.pullChainFile);
		} else {
			throw new Exception("ERROR: wrong PULL expression.");
		}
	}

	private void readPullChainFile (String fileName) throws IOException {
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

	private void readPullChainLine(String line) {
		try {
			Matcher m = Pattern.compile(REG_PULL_OBJ_EXP).matcher(line);
			if (m.find()) {
				pullObjs.put(m.group(1), new PullObj(m.group(1), m.group(2)));
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	public String toString() {
		String result = "==== Pull Chain ====\n";
		if (pullChainFile != null) result += "Pull Chain File: " + this.pullChainFile + "\n";
		if (pullChain != null)    result += "Pull Chain     : " + this.pullChain + "\n";
		result += "Pull Objs: \n";
		for (String key : this.pullObjs.keySet()) {
			result += this.pullObjs.get(key);
		}
		return result;
	}
}
