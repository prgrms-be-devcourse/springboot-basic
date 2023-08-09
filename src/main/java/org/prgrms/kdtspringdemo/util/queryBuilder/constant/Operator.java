package org.prgrms.kdtspringdemo.util.queryBuilder.constant;

public enum Operator {
    EQUALS("="),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<=")
    ;

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
