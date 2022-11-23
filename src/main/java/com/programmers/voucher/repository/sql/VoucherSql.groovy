package com.programmers.voucher.repository.sql

class VoucherSql {
    public static final String FIND_VOUCHER_BY_ID = """
        SELECT v.voucher_id, v.assigned, r.voucher_type, r.voucher_value FROM voucher v 
        INNER JOIN voucher_rule r 
        ON v.voucher_id = r.voucher_id
        WHERE v.voucher_id = UUID_TO_BIN(:voucherId)
    """

    public static final String FIND_ALL_VOUCHERS = """
        SELECT v.voucher_id, v.assigned, r.voucher_type, r.voucher_value FROM voucher v 
        INNER join voucher_rule r 
        ON v.voucher_id = r.voucher_id;
    """

    public static final String INSERT_VOUCHER = """
        INSERT INTO voucher(voucher_id, create_at, assigned)
        VALUES(UUID_TO_BIN(:voucherId), :createAt, :isAssigned)
    """

    public static final String INSERT_VOUCHER_RULE = """
        INSERT INTO voucher_rule(voucher_id, voucher_type, voucher_value)
        VALUES(UUID_TO_BIN(:voucherId), :voucherType, :voucherValue)
    """

    public static final String DELETE_ALL = """
        delete from voucher
    """

    public static final String DELETE_VOUCHER = """
        delete from voucher where voucher_id = UUID_TO_BIN(:voucherId)
    """

    public static final String UPDATE_ASSIGN = """
        update voucher set assigned = :assigned
        where voucher_id = UUID_TO_BIN(:voucherId)
    """

}
