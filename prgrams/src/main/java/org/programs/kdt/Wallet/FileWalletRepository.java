package org.programs.kdt.Wallet;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("staging")
/**
 * 미구현
 */
public class FileWalletRepository implements WalletRepository{
    @Override
    public List<Wallet> findAll() {
        return null;
    }

    @Override
    public Wallet insert(Wallet wallet) {
        return null;
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return null;
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public Optional<Wallet> findById(UUID voucherWalletId) {
        return Optional.empty();
    }

    @Override
    public List<Wallet> findByCustomerEmail(String email) {
        return null;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {

    }

    @Override
    public void deleteById(UUID walletId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existWalletId(UUID walletId) {
        return false;
    }
}
