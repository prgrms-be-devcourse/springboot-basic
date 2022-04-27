package com.programmers.order.utils;

import java.util.regex.Pattern;

public class PatternUtils {

	private PatternUtils() {
	}

	private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");

	public static boolean isNumeric(String argument) {
		return NUMERIC_PATTERN.matcher(argument).matches();
	}

}
