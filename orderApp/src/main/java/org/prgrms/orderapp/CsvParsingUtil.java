package org.prgrms.orderapp;

public class CsvParsingUtil {

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
