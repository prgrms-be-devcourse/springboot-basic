package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

public enum CustomerTable {

    CUSTOMER_ID("customer_id"),
    NAME("name");

    private final String columnName;

    CustomerTable(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
