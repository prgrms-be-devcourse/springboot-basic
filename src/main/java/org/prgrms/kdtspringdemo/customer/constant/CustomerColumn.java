package org.prgrms.kdtspringdemo.customer.constant;

public enum CustomerColumn {
    CUSTOMER_ID("customer_id"),
    NICKNAME("nickname"),
    ;

    private final String column;

    CustomerColumn(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
