package org.prgrms.kdt.engine.voucher.repository;

public enum VoucherSql {
    SELECT_BY_ID("SELECT * FROM vouchers where voucher_id = UUID_TO_BIN(:voucherId)"),
    SELECT_ALL("SELECT * FROM vouchers"),
    INSERT("INSERT INTO vouchers (voucher_id, rate, type) VALUE (UUID_TO_BIN(:voucherId), :rate, :type)"),
    DELETE_ALL("DELETE FROM vouchers"),
    UPDATE_CUSTOMER_ID("UPDATE vouchers SET customer_id = UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)");

    private String sql;

    VoucherSql(String sql) {
        this.sql =sql;
    }

    public String getSql() {
        return sql;
    }
}
