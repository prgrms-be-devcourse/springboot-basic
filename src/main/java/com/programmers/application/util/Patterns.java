package com.programmers.application.util;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern NUMBER_PATTERN = Pattern.compile("\\d");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private Patterns() {
    }
}
