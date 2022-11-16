package com.programmers.voucher.repository.sql

class VoucherSql {
    public static final String FIND_VOUCHER_BY_ID = """
        SELECT * FROM voucher v 
        LEFT JOIN voucher_rule r 
        ON v.voucher_id = r.voucher_id
        WHERE v.voucher_id = UUID_TO_BIN(:voucherId)
    """

    public static final String FIND_ALL_VOUCHERS = """
        SELECT * FROM voucher v 
        left join voucher_rule r 
        ON v.voucher_id = r.voucher_id
    """

    public static final String INSERT_VOUCHER = """
        INSERT INTO voucher(voucher_id, create_at)
        VALUES(UUID_TO_BIN(:voucherId), :createAt)
    """

    public static final String INSERT_VOUCHER_RULE = """
        INSERT INTO voucher_rule(voucher_id, voucher_type, voucher_value)
        VALUES(UUID_TO_BIN(:voucherId), :voucherType, :voucherValue)
    """

    public static final String DELETE_ALL = """
        delete from voucher
    """
}
