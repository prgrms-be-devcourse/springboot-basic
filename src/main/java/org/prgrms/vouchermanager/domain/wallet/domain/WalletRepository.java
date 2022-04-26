package org.prgrms.vouchermanager.domain.wallet.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    void insert(Wallet wallet);

    List<Wallet> findByCustomerId(UUID customerId);

    Optional<Wallet> findByVoucherId(UUID voucherId);

    void delete(Wallet wallet);

    void deleteByVoucherId(UUID voucherId);
}
