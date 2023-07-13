package kr.co.springbootweeklymission.wallet.domain.repository;

import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Wallet save(Wallet wallet);

    List<Wallet> findAllByMemberId(UUID memberId);

    Optional<Wallet> findByVoucherId(UUID voucherId);

    void deleteByVoucherIdAndMemberId(UUID voucherId, UUID memberId);
}
