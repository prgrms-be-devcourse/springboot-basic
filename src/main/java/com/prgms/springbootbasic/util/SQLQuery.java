package com.prgms.springbootbasic.util;

public enum SQLQuery {

    FIND_ALL_VOUCHER("select * from vouchers"),
    FIND_ONE_VOUCHER("select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)"),
    INSERT_VOUCHER("insert into vouchers(voucher_id, type, amount) values (UUID_TO_BIN(:voucherId), :type, :amount)"),
    DELETE_VOUCHER("delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)"),
    UPDATE_VOUCHER("update vouchers set amount = :amount where voucher_id = UUID_TO_BIN(:voucherId)");

    private final String query;

    SQLQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
