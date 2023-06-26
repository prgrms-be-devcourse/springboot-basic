package org.devcourse.springbasic.util;

import java.util.regex.Pattern;

public class RegexPattern {

    private RegexPattern() {}

    public static final Pattern NOT_INPUT_MENU_PATTERN = Pattern.compile("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]");
    public static final Pattern VOUCHER_MENU_PATTERN = Pattern.compile("[0-9]");
}
