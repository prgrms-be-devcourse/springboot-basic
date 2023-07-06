package kr.co.programmers.springbootbasic.wallet.repository;

import kr.co.programmers.springbootbasic.wallet.domain.Wallet;

import java.util.UUID;

public interface WalletRepository {
    void saveVoucherInCustomerWallet(UUID walletId, UUID voucherId);

    Wallet findAllVouchersById(UUID walletId);
}
