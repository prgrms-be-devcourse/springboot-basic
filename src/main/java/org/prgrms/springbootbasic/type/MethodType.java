package org.prgrms.springbootbasic.type;

import java.util.Arrays;

public enum MethodType {
    CREATE, LOOKUP, UPDATE, DELETE, EXIT;

    public static boolean isExit(String input) {
        return EXIT.name().toLowerCase().equals(input);
    }

    public static boolean isCreate(String input) {
        return CREATE.name().toLowerCase().equals(input);
    }

    public static boolean isUpdate(String input) {
        return UPDATE.name().toLowerCase().equals(input);
    }

    public static boolean isDelete(String input) {
        return DELETE.name().toLowerCase().equals(input);
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