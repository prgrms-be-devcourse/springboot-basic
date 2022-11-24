package com.example.springbootbasic.repository.voucher;

public enum JdbcVoucherSql {

    INPUT_VOUCHER_SQL("INSERT INTO VOUCHER(voucher_type, voucher_discount_value) VALUES(:voucherType, :voucherDiscountValue)"),

    SELECT_VOUCHER_BY_ID("SELECT * FROM VOUCHER WHERE voucher_id = :voucherId"),
    SELECT_ALL_VOUCHERS("SELECT * FROM VOUCHER"),
    SELECT_ALL_VOUCHERS_BY_TYPE("SELECT * FROM VOUCHER WHERE voucher_type = :voucherType"),

    UPDATE_VOUCHER_TYPE_BY_VOUCHER_ID("UPDATE VOUCHER SET voucher_type = :voucherType, voucher_discount_value = :voucherDiscountValue WHERE voucher_id = :voucherId"),

    DELETE_ALL_VOUCHERS("DELETE FROM VOUCHER"),
    DELETE_VOUCHERS_BY_TYPE("DELETE FROM VOUCHER WHERE voucher_type = :voucherType"),
    DELETE_VOUCHER_BY_ID("DELETE FROM VOUCHER WHERE voucher_id = :voucherId");

    private final String sql;

    JdbcVoucherSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
