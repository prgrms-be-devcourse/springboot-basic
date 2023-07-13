package prgms.spring_week1.domain.voucher.repository.impl.sql;

public final class VoucherManageSql {
    public static final String insertNewVoucherSQL = "INSERT INTO voucher(voucher_id,voucher_type, discount, created_at) VALUES (UUID_TO_BIN(:voucherId),:voucherType, :discount ,:createdAt)";
    public static final String findAllVoucherSQL = "select * from voucher";
    public static final String findVoucherByTypeSQL = "select * from voucher WHERE voucher_type = :voucherType";
    public static final String deleteAllVoucherSQL = "DELETE FROM voucher";
}
