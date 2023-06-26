package org.devcourse.springbasic.global.util;

public class DigitChecker {

    public static boolean isDigit(String param) {
        try {
            Integer.parseInt(param);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
