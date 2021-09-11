package org.programmers.kdt.utils;

public class DigitUtils {

    public static boolean isDigits(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
