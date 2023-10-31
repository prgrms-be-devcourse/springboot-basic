package org.prgrms.prgrmsspring.repository;

public enum Field {

    CUSTOMER_ID("CUSTOMER_ID"),
    NAME("NAME"),
    IS_BLACK("IS_BLACK"),
    EMAIL("EMAIL"),
    VOUCHER_ID("VOUCHER_ID"),
    TYPE("TYPE"),
    AMOUNT("AMOUNT"),
    CREATE_TIME("CREATE_TIME");


    private final String fieldName;

    Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
