package org.prgrms.kdtspringdemo.util.queryBuilder.constant;

public enum Table {
    VOUCHER("voucher"),
    CUSTOMER("customer")
    ;

    private final String table;

    Table(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}
