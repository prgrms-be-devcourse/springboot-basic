package org.prgms.management.service.wallet;

import org.prgms.management.model.wallet.Wallet;
import org.prgms.management.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet insertWallet(Wallet wallet) {
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

    public Wallet delete(Wallet wallet) {
        return walletRepository.delete(wallet);
    }

    void deleteAll() {
        walletRepository.deleteAll();
    }
}
