package com.prgms.VoucherApp.util;

import java.util.regex.Pattern;

public class UUIDFormatChecker {

    private static final String EXPRESSION_UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    public static final Pattern UUID_REGEX_COMPILE = Pattern.compile(EXPRESSION_UUID_REGEX);

    private UUIDFormatChecker() {

    }

}
