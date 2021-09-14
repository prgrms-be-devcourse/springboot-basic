package org.prgrms.kdt.engine.voucher.repository;

public enum VoucherSql {
    SELECT_BY_ID("SELECT * FROM vouchers where voucher_id = UUID_TO_BIN(:voucherId)"),
    SELECT_ALL("SELECT * FROM vouchers"),
    INSERT("INSERT INTO vouchers (voucher_id, rate, type) VALUE (UUID_TO_BIN(:voucherId), :rate, :type)"),
    DELETE_ALL("DELETE FROM vouchers"),
    UPDATE_CUSTOMER_ID("UPDATE vouchers SET customer_id = UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)"),
    SELECT_CUSTOMER_VOUCHERS("SELECT * FROM vouchers where customer_id = UUID_TO_BIN(:customerId)"),
    DELETE_CUSTOMER_VOUCHER("DELETE FROM vouchers WHERE customer_id = UUID_TO_BIN(:customerId)"),
    SELECT_CUSTOMERID_BY_VOUCHERID("SELECT customer_id FROM vouchers WHERE voucher_id = :voucherId"),
    DELETE_VOUCHER("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)"),
    SELECT_VOUCHERS_BY_TYPE("SELECT * FROM vouchers where type = :type"),
    SELECT_VOUCHERS_BY_CREATED_DATE("SELECT * FROM vouchers WHERE created_at >= :createdDate AND created_at < DATE_ADD(:createdDate, interval 1 day)");

    private final String sql;

    VoucherSql(String sql) {
        this.sql =sql;
    }

    public String getSql() {
        return sql;
    }
}
