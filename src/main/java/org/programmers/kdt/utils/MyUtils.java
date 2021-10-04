package org.programmers.kdt.utils;

public class MyUtils {
    public static boolean isDigits(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
