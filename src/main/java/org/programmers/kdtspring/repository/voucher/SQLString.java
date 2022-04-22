package org.programmers.kdtspring.repository.voucher;

public class SQLString {

    public static final String SELECT_ALL = "select * from vouchers";
    public static final String SELECT_BY_CUSTOMER = "select * from vouchers where customer_id = ?";
    public static final String SELECT_BY_ID = "select * from vouchers where voucher_id = ?";

    public static final String INSERT_VOUCHER = "insert into vouchers(voucher_id,customer_id, voucher_type, amount, percent) values(?, ?, ?, ?, ?)";

    public static final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = ?";

    public static final String DELETE_ALL = "delete from vouchers";
    public static final String DELETE_VOUCHER = "delete from vouchers where voucher_id = ?";

    private SQLString() {

    }
}
