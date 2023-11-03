package com.programmers.vouchermanagement.voucher.repository;

public class VoucherQuery {
    public static final String INSERT = "INSERT INTO test.vouchers(id, type, discount_value) VALUES (UUID_TO_BIN(:id), :type, :discount_value)";
    public static final String FIND_BY_ID = "SELECT * FROM test.vouchers WHERE id = UUID_TO_BIN(:id)";
    public static final String FIND_ALL = "SELECT * FROM test.vouchers";
    public static final String DELETE_VOUCHER = "DELETE FROM test.vouchers WHERE id = UUID_TO_BIN(:id)";
    public static final String DELETE_ALL = "DELETE FROM test.vouchers";
    public static final String UPDATE_VOUCHER = "UPDATE test.vouchers SET type = :type, discount_value = :discount_value WHERE id = UUID_TO_BIN(:id)";
}
