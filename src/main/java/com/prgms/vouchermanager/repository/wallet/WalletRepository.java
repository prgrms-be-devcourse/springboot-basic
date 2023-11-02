package com.prgms.vouchermanager.repository.wallet;

import com.prgms.vouchermanager.domain.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    List<Wallet> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);

    Optional<Wallet> findByVoucherId(UUID voucherId);
}
