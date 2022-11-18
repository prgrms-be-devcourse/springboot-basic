package org.programmers.weekly.mission.domain.wallet;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWallet(UUID customerId, UUID voucherId) {
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId, voucherId);
        walletRepository.insert(wallet);
    }
}
