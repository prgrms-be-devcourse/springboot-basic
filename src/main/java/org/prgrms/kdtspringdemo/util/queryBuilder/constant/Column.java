package org.prgrms.kdtspringdemo.util.queryBuilder.constant;

public enum Column {
    VOUCHER_ID("voucher_id"),
    VOUCHER_TYPE("voucher_type"),
    AMOUNT("amount"),
    ALL("*")
    ;

    private final String column;

    Column(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
