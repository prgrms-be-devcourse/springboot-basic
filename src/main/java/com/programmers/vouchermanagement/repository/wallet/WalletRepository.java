package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void save(Wallet wallet);

    void saveAll(List<Wallet> wallets);

    Optional<Wallet> findById(int id);
    Optional<Wallet> findByCustomerIdAndVoucherId(UUID customerId, UUID voucherId);

    List<Wallet> findAll(GetWalletsRequestDto request);

    void deleteById(int id);

    void deleteAll();
}
