package org.prgrms.orderapp;

import org.prgrms.orderapp.model.Voucher;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {

    public static long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
    public static String[] splitCSV(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    public static String removeSideQuotes(String s) {
        int left = 0, right = s.length()-1;
        while(s.charAt(left) == '"') {
            left++;
        }
        while(s.charAt(right) == '"') {
            right--;
        }
        return s.substring(left, right+1);
    }
}
