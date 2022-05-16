package com.programmers.springbootbasic.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexMatcherUtils {

    private RegexMatcherUtils() {}

    public static boolean isMatched(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
