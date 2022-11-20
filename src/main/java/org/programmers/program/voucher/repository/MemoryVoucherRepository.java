package org.programmers.program.voucher.repository;

import org.programmers.program.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage;

    public MemoryVoucherRepository() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public int update(Voucher voucher) {
        if (!storage.containsKey(voucher.getVoucherId()))
            return -1;
        storage.put(voucher.getVoucherId(), voucher);
        return 1;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        if (storage.containsKey(id))
            return Optional.of(storage.get(id));
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public int count() {
        return storage.size();
    }
}
