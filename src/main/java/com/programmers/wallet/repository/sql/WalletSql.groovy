package com.programmers.wallet.repository.sql

class WalletSql {

    public static final String INSERT_WALLET = """
        INSERT INTO wallet(customer_id, voucher_id, assign_at)
        VALUES (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :assignAt)
    """

    public static final String FIND_VOUCHERS_WITH_CUSTOMER_ID = """
        SELECT * FROM wallet w
        JOIN voucher v ON w.voucher_id = v.voucher_id
        JOIN voucher_rule r ON v.voucher_id = r.voucher_id
        WHERE w.customer_id = UUID_TO_BIN(:customerId)
    """

    public static final String FIND_CUSTOMER_WITH_VOUCHER_ID = """
        SELECT * FROM customer c
        JOIN wallet w ON c.customer_id = w.customer_id
        WHERE w.voucher_id = UUID_TO_BIN(:voucherId)
    """

    public static final String DELETE_CUSTOMER_VOUCHER = """
        delete from wallet where voucher_id = UUID_TO_BIN(:voucherId)
    """

    public static final String DELETE_ALL_WALLET = """
        delete from wallet
    """
}
