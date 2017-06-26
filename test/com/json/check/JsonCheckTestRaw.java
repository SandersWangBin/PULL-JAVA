package com.json.check;

import com.sanderswangbin.pull.api.PullChain;
import com.sanderswangbin.pull.api.PullObj;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCheckTestRaw {
	
	public boolean checkUseRegex(String regex, String text, String[] checks) {
		boolean result = true;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				result = result && (checks[i-1].equals(m.group(i)));
			}
		} else {
			result = false;
		}
		return result;
	}

	public List<Boolean> checkUsePull(String pullExp, String... texts) {
		List<Boolean> results = new ArrayList<Boolean>();
		try {
		    PullObj p = new PullObj(pullExp);
		    for (String text : texts) {
		        results.add(p.check(text).result());
		    }
		} catch (Exception e) {
			System.out.print(e);
		}
		return results;
	}

	public List<Boolean> checkUsePullChain(String pullChainExp, String... texts) {
		List <Boolean> results = new ArrayList<Boolean>();
		try {
		    PullChain pChain = new PullChain(pullChainExp);
		    for (String text : texts) {
		    	results.add(pChain.check(text).result());
//		    	System.out.println(pChain);
		    }
		} catch (Exception e) {
			System.out.println(e);
		}
		return results;
	}
}
