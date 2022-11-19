package org.prgrms.kdt.customer.utils;

public enum CustomerSql {

    FIND_ALL("SELECT * FROM customers"),
    FIND_BY_ID("SELECT * FROM customers WHERE customer_id = ?"),
    UPDATE("UPDATE customers SET name = ? where customer_id = ?"),
    DELETE_ALL("DELETE FROM customers"),
    DELETE_BY_ID("DELETE FROM customers WHERE customer_id = ?");

    private final String sql;

    public String getSql() {
        return sql;
    }

    CustomerSql(String sql) {
        this.sql = sql;
    }
}
