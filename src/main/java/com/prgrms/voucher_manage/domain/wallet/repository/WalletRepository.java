package com.prgrms.voucher_manage.domain.wallet.repository;

import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Wallet save(Wallet wallet);

    Optional<Wallet> findByIds(UUID customerId, UUID voucherId);

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    void delete(Wallet wallet);
}
