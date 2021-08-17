package org.programmers.kdt.utils;

public class MyUtils {
    public static boolean isDigits(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
