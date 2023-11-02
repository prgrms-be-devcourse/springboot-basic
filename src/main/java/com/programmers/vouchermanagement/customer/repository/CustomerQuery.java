package com.programmers.vouchermanagement.customer.repository;

public class CustomerQuery {
    public static final String FIND_ALL_BLACK_CUSTOMER = "SELECT * FROM test.customers WHERE black = TRUE";
    public static final String INSERT = "INSERT INTO test.customers(id, name, black) VALUES (UUID_TO_BIN(:id), :name, :black)";
    public static final String FIND_ALL = "SELECT * FROM test.customers";
}
