package org.prgrms.kdt.util;

import java.util.regex.Pattern;

public class ValidatorUtil {

    public static final String NUMBER_REGEX = "^[\\+\\-]?\\d*(\\.?\\d)*$";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

    private ValidatorUtil() {
    }

    public static boolean isNumeric(String number) {

        if (number == null || number.isBlank()) {
            return false;
        }

        return NUMBER_PATTERN.matcher(number).matches();
    }
}
