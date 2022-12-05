package org.prgrms.springorder.util;

import java.util.regex.Pattern;

public class UUIDValidator {

    private UUIDValidator(){}

    public static void validateString(String uuidStr) {
        Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        if (!UUID_REGEX.matcher(uuidStr).matches()) {
            throw new IllegalArgumentException("id 가 잘못되었습니다. input value: " + uuidStr);
        }
    }

}
