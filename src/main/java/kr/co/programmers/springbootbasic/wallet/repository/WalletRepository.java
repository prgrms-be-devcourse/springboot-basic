package kr.co.programmers.springbootbasic.wallet.repository;

import kr.co.programmers.springbootbasic.wallet.domain.Wallet;

public interface WalletRepository {
    void saveVoucherInCustomerWallet(String walletId, String voucherId);

    Wallet findAllVouchersById(String walletId);
}
