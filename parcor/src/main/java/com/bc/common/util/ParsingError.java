package com.bc.common.util;

public class ParsingError {

	private int line;
	private String detail;

	private ParsingError() {
	}
	
	private ParsingError(int line, String detail) {
		this.line = line;
		this.detail = detail;
	}

	public static ParsingError createParsingError(int line, String detail) {
		return new ParsingError(line, detail);
	}
	
	public String getErrorText() {
		return "Line " + line + ": " + detail;
	}
	
	public int getLine() {
		return line;
	}

	public String getDetail() {
		return detail;
	}

	@Override
	public String toString() {
		return getErrorText();
	}
}
