package org.prgrms.springbootbasic.type;

import java.util.Arrays;

public enum MethodType {
    CREATE, LOOKUP, EXIT;

    public static boolean isExist(String input) {
        return EXIT.name().toLowerCase().equals(input);
    }

    public static boolean isCreateVoucher(String input) {
        return CREATE.name().toLowerCase().equals(input);
    }

    public static boolean isLookupList(String input) {
        return LOOKUP.name().toLowerCase().equals(input);
    }

    public static boolean validate(String input) {
        return Arrays.stream(MethodType.values())
                .map(MethodType::name)
                .map(String::toLowerCase)
                .anyMatch(choice -> choice.equals(input));
    }
}