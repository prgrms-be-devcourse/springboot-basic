package com.example.springbootbasic.repository.customer;

public enum CustomerParam {
    CUSTOMER_ID("customerId", "customer_id"),
    CUSTOMER_STATUS("customerStatus", "customer_status");

    private final String param;
    private final String column;

    CustomerParam(String param, String column) {
        this.param = param;
        this.column = column;
    }

    public String getParam() {
        return param;
    }

    public String getColumn() {
        return column;
    }
}
