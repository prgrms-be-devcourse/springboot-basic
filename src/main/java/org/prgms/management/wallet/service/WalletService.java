package org.prgms.management.wallet.service;

import org.prgms.management.wallet.vo.Wallet;
import org.prgms.management.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<Wallet> insertWallet(Wallet wallet) {
        return walletRepository.insert(wallet);
    }

    public List<Wallet> getAllWallet() {
        return walletRepository.findAll();
    }

    public List<Wallet> getById(UUID walletId) {
        return walletRepository.findById(walletId);
    }

    public List<Wallet> getByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId);
    }

    public Optional<Wallet> delete(Wallet wallet) {
        return walletRepository.delete(wallet);
    }

    void deleteAll() {
        walletRepository.deleteAll();
    }
}
