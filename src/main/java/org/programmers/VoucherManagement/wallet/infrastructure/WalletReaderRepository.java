package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletReaderRepository {
    /**
     * walletId를 이용해 Wallet 조회
     *
     * @param walletId
     * @return Optional<Wallet> - walletId를 이용해 조회한 Wallet
     */
    Optional<Wallet> findById(UUID walletId);

    /**
     * memberId를 이용해 Wallet 조회
     *
     * @param memberId
     * @return List<Wallet> - memberId를 이용해 조회한 특정 회원이 가지고 있는 Wallet 리스트
     */
    List<Wallet> findAllByMemberId(String memberId);

    /**
     * voucherId를 이용해 Wallet 조회
     *
     * @param voucherId
     * @return List<Wallet> - voucherId를 이용해 wallet에 저장된 Voucher 리스트
     */
    List<Wallet> findAllByVoucherId(UUID voucherId);
}
