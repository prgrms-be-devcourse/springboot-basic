package org.prgms.management.wallet.service;

import org.prgms.management.wallet.entity.Wallet;
import org.prgms.management.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Map<UUID, Wallet> getAllWallet() {
        return walletRepository.getAll();
    }

    public Optional<Wallet> insertWallet(Wallet wallet) {
        return walletRepository.insert(wallet);
    }
}
