package com.prgrms.voucher_manage.domain.wallet.repository;

import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    Wallet save(Wallet wallet);
    List<Wallet> findByCustomerId(UUID customerId);
    List<Wallet> findByVoucherId(UUID voucherId);
    int deleteById(UUID customerId, UUID voucherId);
}
