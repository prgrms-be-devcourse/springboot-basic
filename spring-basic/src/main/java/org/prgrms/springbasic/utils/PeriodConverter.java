package org.prgrms.springbasic.utils;

import java.util.List;

public class PeriodConverter {

    private static final String regex = "[^0-9]";

    private static final String dateRegex =  "(\\d{4})(\\d{2})(\\d{2})";

    public static List<String> of(String from, String to) {
        String convertedFrom = from.replaceAll(regex, "")
                .replaceAll(dateRegex, "$1-$2-$3");


        String convertedTo= to.replaceAll(regex, "")
                .replaceAll(dateRegex, "$1-$2-$3");

        return List.of(convertedFrom, convertedTo);
    }
}