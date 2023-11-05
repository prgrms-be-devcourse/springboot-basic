package com.programmers.vouchermanagement.wallet.repository;

public class WalletQuery {
    public static final String INSERT = "INSERT INTO test.ownership(voucher_id, customer_id) VALUES (UUID_TO_BIN(:voucher_id), UUID_TO_BIN(:customer_id))";
    public static final String DELETE_OWNERSHIP = "DELETE FROM test.ownership WHERE voucher_id = UUID_TO_BIN(:id)";
    public static final String DELETE_ALL = "TRUNCATE TABLE test.ownership";
    public static final String FIND_CUSTOMER_BY_VOUCHER_ID = "SELECT c.* FROM test.ownership as o JOIN test.customers as c ON o.customer_id = c.id WHERE o.voucher_id = uuid_to_bin(:id)";
    public static final String FIND_ALL_VOUCHER_BY_CUSTOMER_ID = "SELECT v.*  FROM test.ownership as o JOIN test.vouchers as v ON o.voucher_id = v.id WHERE o.customer_id = uuid_to_bin(:id)";
}
