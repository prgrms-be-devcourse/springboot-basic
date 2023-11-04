package org.programmers.springboot.basic.domain.wallet.repository;

import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    void save(Wallet wallet);
    List<Wallet> findByEmail(Email email);
    List<Wallet> findById(UUID voucherId);
    Optional<Wallet> findByEmailNId(Email email, UUID voucherId);
    void delete(Wallet wallet);
    void deleteAll();
}
