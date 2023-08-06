package com.devcourse.global.utils;

public class CaseConverter {
    private static final String UNDER_BAR = "_";

    private CaseConverter() {
    }

    public static String toSnakeCase(Class<?> from) {
        char[] chars = extractCharArray(from);

        return convertCase(chars) + "s";
    }

    public static String toSnakeCase(String input) {
        char[] chars = input.toCharArray();
        return convertCase(chars);
    }

    private static String convertCase(char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toLowerCase(chars[0]));

        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                stringBuilder.append(UNDER_BAR);
            }
            stringBuilder.append(Character.toLowerCase(chars[i]));
        }

        return stringBuilder.toString();
    }

    private static char[] extractCharArray(Class<?> from) {
        String name = from.getSimpleName();
        return name.toCharArray();
    }
}
