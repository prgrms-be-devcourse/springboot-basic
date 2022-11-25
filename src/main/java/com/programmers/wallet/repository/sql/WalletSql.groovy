package com.programmers.wallet.repository.sql

class WalletSql {

    public static final String INSERT_WALLET = """
        INSERT INTO wallet(customer_id, voucher_id)
        VALUES (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))
    """

    public static final String FIND_VOUCHERS_WITH_CUSTOMER_ID = """
        SELECT w.voucher_id, v.assigned, r.voucher_type, r.voucher_value FROM wallet w
        JOIN voucher v ON w.voucher_id = v.voucher_id
        JOIN voucher_rule r ON v.voucher_id = r.voucher_id
        WHERE w.customer_id = UUID_TO_BIN(:customerId);
    """

    public static final String FIND_CUSTOMER_WITH_VOUCHER_ID = """
            SELECT c.customer_id, c.name, c.email, c.create_at, c.last_login_at, v.voucher_id, v.assigned, r.voucher_type, r.voucher_value
            FROM customer c
            LEFT JOIN wallet w
            ON c.customer_id = w.customer_id
            LEFT JOIN voucher v
            on w.voucher_id = v.voucher_id
            LEFT JOIN voucher_rule r
            on v.voucher_id = r.voucher_id
            WHERE v.voucher_id = UUID_TO_BIN(:voucherId);
    """

    public static final String DELETE_CUSTOMER_VOUCHER = """
        delete from wallet where voucher_id = UUID_TO_BIN(:voucherId)
    """

    public static final String DELETE_ALL_WALLET = """
        delete from wallet
    """
}
