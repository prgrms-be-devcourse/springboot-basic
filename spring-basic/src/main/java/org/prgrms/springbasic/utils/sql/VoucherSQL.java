package org.prgrms.springbasic.utils.sql;

public enum VoucherSQL {

    CREATE_VOUCHER(
            "insert into " +
                    "vouchers(voucher_id, voucher_type, discount_info, created_at, modified_at, customer_id) " +
                    "values(UUID_TO_BIN(:voucherId), :voucherType, :discountInfo, :createdAt, :modifiedAt, UUID_TO_BIN(:customerId))"
    ),
    SELECT_BY_VOUCHER_ID(
            "select * from vouchers " +
                    "where voucher_id = UUID_TO_BIN(:voucherId)"
    ),
    SELECT_BY_CUSTOMER_ID(
            "select * from vouchers where customer_id = UUID_TO_BIN(:customerId)"
    ),
    SELECT_BY_VOUCHER_TYPE(
            "select * from vouchers where voucher_type = :voucherType"
    ),
    SELECT_VOUCHERS(
            "select * from vouchers order by created_at"
    ),
    SELECT_WALLETS(
            "select * from wallets"
    ),
    SELECT_COUNT(
            "select count(*) from vouchers"
    ),
    UPDATE_VOUCHER(
            "update vouchers set " +
                    "voucher_type = :voucherType, " +
                    "discount_info = :discountInfo, " +
                    "modified_at = :modifiedAt, " +
                    "customer_id = UUID_TO_BIN(:customerId) " +
                    "where voucher_id = UUID_TO_BIN(:voucherId)"
    ),
    DELETE_BY_VOUCHER_ID(
            "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)"
    ),
    DELETE_BY_CUSTOMER_ID(
            "delete from vouchers where customer_id = UUID_TO_BIN(:customerId)"
    ),
    DELETE_VOUCHERS(
            "delete from vouchers"
    );

    private final String query;

    VoucherSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
