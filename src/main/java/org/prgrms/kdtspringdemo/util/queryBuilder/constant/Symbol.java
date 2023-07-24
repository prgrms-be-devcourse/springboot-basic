package org.prgrms.kdtspringdemo.util.queryBuilder.constant;

public enum Symbol {
    BLANK(" "),
    EQUAL("="),
    COMMA(","),
    COLON(":"),
    LEFT_BRACKET("("),
    RIGHT_BRACKET(")")
    ;

    private final String value;

    Symbol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
