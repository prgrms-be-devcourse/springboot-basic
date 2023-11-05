package com.programmers.vouchermanagement.voucher.repository;

public class VoucherQuery {
    public static final String INSERT = "INSERT INTO vouchers(id, type, discount_value) VALUES (UUID_TO_BIN(:id), :type, :discount_value)";
    public static final String FIND_BY_ID = "SELECT * FROM vouchers WHERE id = UUID_TO_BIN(:id)";
    public static final String FIND_ALL = "SELECT * FROM vouchers";
    public static final String FIND_ALL_BY_CREATED_AT = "SELECT * FROM vouchers WHERE created_at BETWEEN DATE(:from) AND DATE(:to)";
    public static final String FIND_ALL_BY_TYPE = "SELECT * FROM vouchers WHERE type = :type";
    public static final String DELETE_VOUCHER = "DELETE FROM vouchers WHERE id = UUID_TO_BIN(:id)";
    public static final String DELETE_ALL = "DELETE FROM vouchers";
    public static final String UPDATE_VOUCHER = "UPDATE vouchers SET type = :type, discount_value = :discount_value WHERE id = UUID_TO_BIN(:id)";
    private VoucherQuery() {
    }
}
