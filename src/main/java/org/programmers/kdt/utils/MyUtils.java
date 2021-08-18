package org.programmers.kdt.utils;

public class MyUtils {
    public static boolean isDigits(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBetweenZeroAndHundred(String str) {
        int percentage = Integer.parseInt(str);
        if (percentage < 0 || percentage > 100) {
            return false;
        }
        return true;
    }
}
