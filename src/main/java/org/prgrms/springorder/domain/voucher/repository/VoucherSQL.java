package org.prgrms.springorder.domain.voucher.repository;

public enum VoucherSQL {

    FIND_BY_ID("SELECT * FROM vouchers WHERE voucher_id = :voucherId"),
    FIND_ALL("SELECT * FROM vouchers"),

    DELETE_ALL("DELETE FROM vouchers"),
    DELETE_BY_ID("DELETE FROM vouchers WHERE voucher_id = :voucherId"),

    INSERT("INSERT INTO vouchers(voucher_id, amount, voucher_type, customer_id, created_at) "
        + "VALUES (:voucherId, :amount, :voucherType, :customerId, :createdAt)"),


    UPDATE_BY_ID("UPDATE vouchers SET amount = :amount, voucher_type = :voucherType, customer_id = :customerId WHERE voucher_id = :voucherId" ),


    FIND_BY_ID_WITH_CUSTOMER("SELECT "
        + " * "
        + " FROM vouchers v "
        + " INNER JOIN customers c ON v.customer_id = c.customer_id  "
        + " WHERE voucher_id = :voucherId");


    private final String sql;

    VoucherSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
