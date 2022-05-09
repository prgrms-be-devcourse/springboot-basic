package com.prgrms.vouchermanagement.customer;

public final class CustomerSql {

    private CustomerSql() {
    }

    public static final String INSERT_SQL = "INSERT INTO customer(name, email, created_at) VALUES(:name, :email, :createdAt)";
    public static final String UPDATE_SQL = "UPDATE customer SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = :customerId";
    public static final String SELECT_SQL = "SELECT * FROM customer";
    public static final String SELECT_BY_ID = "SELECT * FROM customer WHERE customer_id = :customerId";
    public static final String SELECT_BY_NAME_SQL = "SELECT * FROM customer WHERE name = :name";
    public static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM customer WHERE email = :email";
    public static final String DELETE_SQL = "DELETE FROM customer";
    public static final String DELETE_BY_ID_SQL = "DELETE FROM customer WHERE customer_id=:customerId";
    public static final String FIND_CUSTOMER_BY_VOUCHER_SQL = "SELECT c.customer_id, c.name, c.email, c.last_login_at, c.created_at FROM voucher_wallet w INNER JOIN customer c ON c.customer_id = w.customer_id WHERE w.voucher_id=:voucherId";
}
