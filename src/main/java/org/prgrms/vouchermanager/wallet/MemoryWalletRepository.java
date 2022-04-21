package org.prgrms.vouchermanager.wallet;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Wallet은 인메모리 리포지토리로 작성하였습니다.
 */
@Repository
public class MemoryWalletRepository implements WalletRepository {

    private final Map<UUID, Wallet> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(Wallet wallet) {
        if (storage.get(wallet.getWalletId()) != null) throw new IllegalArgumentException("이미 존재하는 Wallet 입니다.");
        storage.put(wallet.getWalletId(), wallet);
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return storage.values().stream().filter(wallet -> wallet.getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    @Override
    public Optional<Wallet> findByVoucherId(UUID voucherId) {
        return storage.values().stream().filter(wallet -> wallet.getVoucherId().equals(voucherId)).findFirst();
    }

    @Override
    public void delete(Wallet wallet) {
        Wallet remove = storage.remove(wallet.getWalletId());
        if (remove == null) throw new IllegalArgumentException("삭제할 wallet이 존재하지 않습니다.");
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        Wallet wallet = findByVoucherId(voucherId).orElseThrow(() -> new IllegalArgumentException("삭제할 wallet이 존재하지 않습니다."));
        delete(wallet);
    }

}
