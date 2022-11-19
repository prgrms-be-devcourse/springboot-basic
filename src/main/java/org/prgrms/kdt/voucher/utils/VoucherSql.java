package org.prgrms.kdt.voucher.utils;

public enum VoucherSql {
    FIND_ALL("SELECT * FROM vouchers"),
    FIND_BY_ID("SELECT * FROM vouchers WHERE voucher_id = ?"),
    UPDATE("UPDATE vouchers SET type = ?, amount = ? WHERE voucher_id = ?"),
    DELETE_ALL("DELETE FROM vouchers"),
    DELETE_BY_ID("DELETE FROM vouchers WHERE voucher_id = ?");

    private final String sql;

    public String getSql() {
        return sql;
    }

    VoucherSql(String sql) {
        this.sql = sql;
    }
}
