package com.bc.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CSVParser {

	private BufferedReader reader;
	private String tokens;
	private StringTokenizer tokenizer;

	public CSVParser(File file, String tokens) {
		this(file.getAbsolutePath(), tokens);
	}

	public CSVParser(String filename, String tokens) {
		this.tokens = tokens;
		try {
			FileReader file1 = new FileReader(new File(filename));
			reader = new BufferedReader(file1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CSVParser(InputStream is, String tokens) {
		this.tokens = tokens;
		reader = new BufferedReader(new InputStreamReader(is));
	}

	public void skipLine() {
		moreLines();
	}

	public boolean moreLines() {
		try {
			String line = reader.readLine();
			if (line != null) {
				tokenizer = new StringTokenizer(line, tokens);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String nextToken() {
		try {
			if (tokenizer.hasMoreTokens()) {
				return tokenizer.nextToken().trim();
			} else {
				return "";
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String[] getTokens() {
		int numTok = tokenizer.countTokens();
		String tokens[] = new String[numTok];
		for (int i = 0; tokenizer.hasMoreTokens(); i++) {
			tokens[i] = nextToken();
		}
		return tokens;
	}
}
