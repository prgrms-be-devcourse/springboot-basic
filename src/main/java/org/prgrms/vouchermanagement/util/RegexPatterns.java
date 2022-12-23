package org.prgrms.vouchermanagement.util;

import java.util.regex.Pattern;

public enum RegexPatterns {
    NUMBER_REGEX(Pattern.compile("\\d+")),
    UUID_REGEX(Pattern.compile("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")),
    NAME_REGEX(Pattern.compile("[a-z|A-Z|[가-힣]|\\s]{1,30}")),
    EMAIL_REGEX(Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"))
    ;

    private final Pattern pattern;

    RegexPatterns(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean isMatch(String input) {
        return pattern.matcher(input).matches();
    }
}
