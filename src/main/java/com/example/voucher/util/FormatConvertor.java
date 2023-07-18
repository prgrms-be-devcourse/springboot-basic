package com.example.voucher.util;

public class FormatConvertor {

    private FormatConvertor() {
    }

    public static String convertToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean upperNext = false;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (currentChar == '_') {
                upperNext = true;
            } else {
                if (upperNext) {
                    result.append(Character.toUpperCase(currentChar));
                    upperNext = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }

        return result.toString();
    }
}
