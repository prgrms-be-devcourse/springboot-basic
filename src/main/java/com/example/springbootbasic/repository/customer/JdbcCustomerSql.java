package com.example.springbootbasic.repository.customer;


public enum JdbcCustomerSql {
    FIND_CUSTOMER_BY_ID("SELECT * FROM CUSTOMER WHERE customer_id = :customerId"),
    FIND_ALL_CUSTOMERS("SELECT * FROM CUSTOMER"),
    FIND_ALL_CUSTOMERS_BY_STATUS("SELECT * FROM CUSTOMER WHERE customer_status = :customerStatus"),
    FIND_CUSTOMER_ALL_VOUCHERS_ID("SELECT voucher_id FROM CUSTOMER_VOUCHER WHERE customer_id = :customerId"),
    FIND_CUSTOMERS_ID_BY_VOUCHER_ID("SELECT customer_id FROM CUSTOMER_VOUCHER WHERE voucher_id = :voucherId"),
    INSERT_CUSTOMER("INSERT INTO CUSTOMER(customer_status) VALUES(:customerStatus)"),
    INSERT_CUSTOMER_VOUCHER("INSERT INTO CUSTOMER_VOUCHER(customer_id, voucher_id) VALUES(:customerId, :voucherId)"),
    DELETE_ALL_CUSTOMERS("DELETE FROM CUSTOMER"),
    DELETE_CUSTOMER_ALL_VOUCHERS("DELETE FROM CUSTOMER_VOUCHER WHERE customer_id = :customerId"),
    DELETE_ALL_CUSTOMER_VOUCHER("DELETE FROM CUSTOMER_VOUCHER"),
    DELETE_CUSTOMER("DELETE FROM CUSTOMER WHERE customer_id = :customerId"),
    DELETE_CUSTOMER_VOUCHER_BY_IDS("DELETE FROM CUSTOMER_VOUCHER WHERE customer_id = :customerId AND voucher_id = :voucherId");

    private final String sql;

    JdbcCustomerSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
