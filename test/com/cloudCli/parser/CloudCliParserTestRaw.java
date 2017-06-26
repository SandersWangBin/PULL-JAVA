package com.cloudCli.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sanderswangbin.pull.api.PullObj;

public class CloudCliParserTestRaw {

	private static final String CLI_CLOUD_01 = 
			"+--------------------------------------+------------+--------------------+----------------------+\n" + 
	        "| id                                   | stack_name | stack_status       | creation_time        |\n" +
			"+--------------------------------------+------------+--------------------+----------------------+\n" + 
            "| 572df0d0-288e-4051-b8eb-f6bb0f002ae5 | CBA_STACK  | CREATE_IN_PROGRESS | 2015-02-16T13:53:50Z |\n" +
            "+--------------------------------------+------------+--------------------+----------------------+\n";

	private static final String REG_CLOUD_01 = 
	        "\\|\\s*([a-z0-9_-]+)\\s*\\|\\s*([A-Z_]+)\\s*\\|\\s*([A-Z_]+)\\s*\\|\\s*([A-Z0-9-:]+)\\s*\\|";

	private static final String PULL_CLOUD_01 = "r\'" +
	        "\\|\\s*([a-z0-9_-]+)\\s*\\|\\s*([A-Z_]+)\\s*\\|\\s*([A-Z_]+)\\s*\\|\\s*([A-Z0-9-:]+)\\s*\\|" +
			"\'.PULL(" + 
	        "{0}==[];{1}==[];{2}==[];{3}==[])";

	public List<String> parserUseRegex(String regex, String text) {
		List<String> result = new ArrayList<String>(); 
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			for (int i = 1; i <=m.groupCount(); i++) {
				result.add(m.group(i));
			}
		}
		return result;
	}
	public static void main(String... argv) {
		CloudCliParserTestRaw ccp = new CloudCliParserTestRaw();
		// Get the values from regex.
		System.out.print("Parser using regex: ");
		for (String r : ccp.parserUseRegex(REG_CLOUD_01, CLI_CLOUD_01)) {
			System.out.print(r + "; ");
		}
		System.out.println();

		// Get the values from PULL.
		try {
			System.out.print("Parser using PULL: ");
			PullObj p = new PullObj(PULL_CLOUD_01);
			p.check(CLI_CLOUD_01);
			for (int i = 0; i < p.getVars().size(); i++) {
				System.out.print(p.getVars().get(i).values().get(0) + "; ");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
