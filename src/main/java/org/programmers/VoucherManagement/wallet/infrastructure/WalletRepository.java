package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    /**
     * db에 wallet 저장
     *
     * @param wallet
     * @return Wallet - 저장 완료한 Wallet
     */
    Wallet insert(Wallet wallet);

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
    List<Wallet> findAllByMemberId(UUID memberId);

    /**
     * voucherId를 이용해 Wallet 조회
     *
     * @param voucherId
     * @return List<Wallet> - voucherId를 이용해 wallet에 저장된 Voucher 리스트
     */
    List<Wallet> findAllByVoucherId(UUID voucherId);

    /**
     * walletId를 이용해 특정 Wallet 삭제
     *
     * @param walletId
     */
    void delete(UUID walletId);
}
