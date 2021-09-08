package org.prgrms.kdt.engine.customer.repository;

public enum CustomerSql {
    COUNT("SELECT COUNT(*) FROM customers"),
    INSERT("INSERT INTO customers (customer_id, name, email, created_at) VALUE (UUID_TO_BIN(:customerId), :name, :email, :createdAt)"),
    UPDATE("UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)"),
    SELECT_ALL("SELECT * FROM customers"),
    SELECT_BY_ID("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)"),
    SELECT_BY_NAME("SELECT * FROM customers WHERE name = :name"),
    SELECT_BY_EMAIL("SELECT * FROM customers WHERE email = :email"),
    DELETE_ALL("DELETE FROM customers");

    private final String sql;

    CustomerSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
