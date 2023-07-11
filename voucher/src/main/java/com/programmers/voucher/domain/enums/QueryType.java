package com.programmers.voucher.domain.enums;

public enum QueryType {
    SELECT_ALL("SELECT * FROM customers"),
    SELECT_BY_ID("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)"),
    SAVE("INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)"),
    UPDATE("UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)"),
    DELETE("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)")
    ;
    public String stringQuery;

    QueryType(String stringQuery) {
        this.stringQuery = stringQuery;
    }
}
