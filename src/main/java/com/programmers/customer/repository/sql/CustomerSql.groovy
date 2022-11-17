package com.programmers.customer.repository.sql

class CustomerSql {
    public static final String SELECT_ALL = """
        SELECT * FROM customer
    """

    public static final String SELECT_BY_EMAIL = """
        SELECT * FROM customer 
        WHERE email = :email
    """

    public static final String SELECT_BY_ID = """
        SELECT * FROM customer
        WHERE customer_id = UUID_TO_BIN(:customerId)
    """

    public static final String SELECT_ALL_BY_ID = """
        SELECT c.customer_id, c.name, c.email, c.create_at, c.last_login_at, v.voucher_id, r.voucher_type, r.voucher_value
        FROM customer c
        LEFT JOIN wallet w
        ON c.customer_id = w.customer_id
        LEFT JOIN voucher v
        on w.voucher_id = v.voucher_id
        LEFT JOIN voucher_rule r
        on v.voucher_id = r.voucher_id
        WHERE c.customer_id = UUID_TO_BIN(:customerId)
    """

    public static final String SELECT_BY_NAME = """
        SELECT * FROM customer WHERE name = :name
    """

    public static final String SELECT_COUNT = """
        SELECT count(*) FROM customer
    """

    public static final String INSERT_CUSTOMER = """
        INSERT INTO customer(customer_id, name, email, create_at) 
        VALUES (UUID_TO_BIN(:customerId), :name ,:email, :createAt)
    """

    public static final String UPDATE_CUSTOMER = """
        UPDATE customer 
            SET name = :name,
            email =:email,
            last_login_at =:lastLoginAt 
            WHERE customer_id = UUID_TO_BIN(:customerId)
    """

    public static final String DELETE_ALL = """
        delete from customer
    """
}
