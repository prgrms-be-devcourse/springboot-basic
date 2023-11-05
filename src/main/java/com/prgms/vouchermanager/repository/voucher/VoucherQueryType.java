package com.prgms.vouchermanager.repository.voucher;

public enum VoucherQueryType {

    SELECT_BY_ID("select * from vouchers WHERE id = :id"),
    SELECT_BY_TYPE("select * from vouchers WHERE type = :type "),
    SELECT_BETWEEN_DATE("select * from vouchers where created_at >= :start_time and created_at <= :end_time"),
    SELECT_ALL("select * from vouchers"),
    INSERT("insert into vouchers (id, discount_value, type) " +
            "values (:id, :discount_value, :type)"),

    UPDATE("update vouchers set id = :id , discount_value = :discount_value, type = :type " +
            "where id = :id"),
    DELETE_BY_ID("delete from vouchers where id = :id"),
    DELETE_ALL("delete from vouchers");

    private final String query;

    VoucherQueryType(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
