package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.wallet.domain.Ownership;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void save(Ownership ownership);

    List<UUID> findAllVoucherByCustomerId(UUID customerId);

    void delete(Ownership ownership);

    Optional<UUID> findCustomerByVoucherId(UUID voucherId);

    void deleteAll();
}
