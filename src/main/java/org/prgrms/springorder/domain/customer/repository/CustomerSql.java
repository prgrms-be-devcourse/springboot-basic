package org.prgrms.springorder.domain.customer.repository;

public enum CustomerSql {

    FIND_BY_ID("SELECT * FROM customers WHERE customer_id = :customerId"),

    INSERT("INSERT INTO customers(customer_id, name, email, last_login_at) "
        + "VALUES (:customerId, :name, :email, :lastLoginAt)"),

    FIND_ALL("SELECT * FROM customers"),

    DELETE_ALL("DELETE FROM customers"),

    UPDATE_BY_ID("UPDATE customers set name = :name, email = :email, customer_status = :customerStatus, last_login_at = :lastLoginAt")
    ;

    private final String sql;

    CustomerSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
