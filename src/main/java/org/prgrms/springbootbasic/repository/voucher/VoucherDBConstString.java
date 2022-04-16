package org.prgrms.springbootbasic.repository.voucher;

public class VoucherDBConstString {

    public static final String SELECT_ALL_SQL = "select * from vouchers";
    public static final String INSERT_SQL = "insert into vouchers(voucher_id, type, amount, percent) values (uuid_to_bin(?), ?, ?, ?)";
    public static final String SELECT_BY_ID_SQL = "select * from vouchers where voucher_id = uuid_to_bin(?)";
    public static final String DELETE_ALL_SQL = "delete from vouchers";
    public static final String SELECT_BY_CUSTOMER_SQL = "select * from vouchers where customer_id = uuid_to_bin(?)";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_VOUCHER_ID = "voucher_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_PERCENT = "percent";


    private VoucherDBConstString() {
        throw new AssertionError("VoucherDBConstString.class는 생성할 수 없습니다.");
    }
}
