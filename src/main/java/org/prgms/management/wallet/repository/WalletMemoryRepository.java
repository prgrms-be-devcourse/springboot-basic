package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.entity.Wallet;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WalletMemoryRepository implements WalletRepository {
    private final Map<UUID, Wallet> walletMap = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Wallet> getAll() {
        return walletMap;
    }

    @Override
    public Optional<Wallet> insert(Wallet wallet) {
        walletMap.put(wallet.getWalletId(), wallet);
        return Optional.of(wallet);
    }
}
