package com.mountain.voucherApp.constants;

public class Query {
    public static final int EXECUTE_SUCCESS = 1;
    // c
    public static final String INSERT_CUSTOMER = "INSERT INTO customers (customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    public static final String INSERT_VOUCHER = "INSERT INTO vouchers (voucher_id, discount_policy_id, discount_amount) VALUES (UUID_TO_BIN(:voucherId), :discountPolicyId, :discountAmount)";

    // r
    public static final String COUNT_CUSTOMER = "select count(*) from customers";
    public static final String SELECT_ALL_CUSTOMER = "select * from customers";
    public static final String SELECT_ALL_VOUCHER = "select * from vouchers";
    public static final String SELECT_CUSTOMER_BY_ID = "select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    public static final String SELECT_VOUCHER_BY_ID = "select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String SELECT_VOUCHER_BY_POLICY_ID_AND_AMOUNT = "select * from vouchers WHERE discount_policy_id = :discountPolicyId and discount_amount = :discountAmount";
    public static final String SELECT_CUSTOMER_BY_NAME = "select * from customers WHERE name = :name";
    public static final String SELECT_CUSTOMER_BY_EMAIL = "select * from customers WHERE email = :email";

    // u
    public static final String UPDATE_CUSTOMER = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = UUID_TO_BIN(:customerId)";
    public static final String UPDATE_VOUCHER = "UPDATE vouchers SET discount_policy_id = :discountPolicyId, discount_amount = :discountAmount where voucher_id = UUID_TO_BIN(:voucherId)";

    // d
    public static final String DELETE_ALL_CUSTOMER = "DELETE FROM customers";

}
