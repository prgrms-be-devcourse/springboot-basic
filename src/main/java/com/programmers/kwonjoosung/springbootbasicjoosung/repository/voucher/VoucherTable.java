package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

public enum VoucherTable {

    VOUCHER_ID("voucher_id"),
    VOUCHER_TYPE("voucher_type"),
    DISCOUNT("discount");

    private final String columnName;

    VoucherTable(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
