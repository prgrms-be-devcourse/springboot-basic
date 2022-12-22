package org.prgrms.vouchermanagement.util;

public enum RegexConstant {
    NUMBER_REGEX("\\d+"),
    UUID_REGEX("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$"),
    NAME_REGEX("[a-z|A-Z|[가-힣]|\\s]{1,30}"),
    EMAIL_REGEX("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    ;

    private final String regex;

    RegexConstant(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
