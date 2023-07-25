package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.UUID;

public interface WalletStoreRepository {
    /**
     * db에 wallet 저장
     *
     * @param wallet
     * @return Wallet - 저장 완료한 Wallet
     */
    Wallet insert(Wallet wallet);

    /**
     * walletId를 이용해 특정 Wallet 삭제
     *
     * @param walletId
     */
    void delete(UUID walletId);
}
