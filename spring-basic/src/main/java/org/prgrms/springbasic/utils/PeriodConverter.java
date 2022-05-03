package org.prgrms.springbasic.utils;

import java.util.List;

public class PeriodConverter {

    private static final String numRegex = "[^0-9]";
    private static final String dateRegex =  "(\\d{4})(\\d{2})(\\d{2})";
    private static final String space = "";
    private static final String replacement = "$1-$2-$3";



    public static List<String> convertToPeriod(List<String> period) {
        return period.stream()
                .map(p -> p.replaceAll(numRegex, space).replaceAll(dateRegex, replacement))
                .toList();
    }
}