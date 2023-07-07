package kr.co.programmers.springbootbasic.wallet.repository;

public class WalletQuery {
    public static final String SAVE_VOUCHER_IN_WALLET
            = "UPDATE voucher SET wallet_id = UUID_TO_BIN(?) WHERE id = UUID_TO_BIN(?)";
    public static final String FIND_ALL_VOUCHERS
            = "SELECT * FROM voucher WHERE wallet_id = UUID_TO_BIN(?)";

    private WalletQuery() {
    }
}
