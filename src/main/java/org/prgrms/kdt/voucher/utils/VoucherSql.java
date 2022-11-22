package org.prgrms.kdt.voucher.utils;

public enum VoucherSql {
    FIND_ALL("SELECT * FROM vouchers"),
    FIND_BY_ID("SELECT * FROM vouchers WHERE voucher_id = :voucherId"),
    UPDATE("UPDATE vouchers SET type = :type, amount = :amount WHERE voucher_id = :voucherId"),
    DELETE_ALL("DELETE FROM vouchers"),
    DELETE_BY_ID("DELETE FROM vouchers WHERE voucher_id = :voucherId");

    private final String sql;

    public String getSql() {
        return sql;
    }

    VoucherSql(String sql) {
        this.sql = sql;
    }
}
