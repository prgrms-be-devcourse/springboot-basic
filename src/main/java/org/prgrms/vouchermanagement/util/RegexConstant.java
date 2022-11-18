package org.prgrms.vouchermanagement.util;

public class RegexConstant {
    public static final String NUMBER_REGEX = "\\d+";
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
    public static final String NAME_REGEX = "[a-z|A-Z|[가-힣]|\\s]{1,30}";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    private RegexConstant() {
    }
}
