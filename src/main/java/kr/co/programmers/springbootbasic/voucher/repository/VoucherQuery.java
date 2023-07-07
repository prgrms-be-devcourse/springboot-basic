package kr.co.programmers.springbootbasic.voucher.repository;

public class VoucherQuery {
    public static final String CREATE_VOUCHER
            = "INSERT INTO voucher (id, type_id, amount, wallet_id) VALUES (UUID_TO_BIN(?), ?, ?, ?)";
    public static final String FIND_VOUCHER_BY_ID = "SELECT * FROM voucher WHERE id = UUID_TO_BIN(?)";
    public static final String LIST_ALL = "SELECT * FROM voucher";
    public static final String DELETE_BY_ID = "DELETE FROM voucher WHERE id = UUID_TO_BIN(?)";

    private VoucherQuery() {
    }
}
