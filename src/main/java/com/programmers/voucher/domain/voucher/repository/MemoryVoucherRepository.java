package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.getType() == voucherType)
                .toList();
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        storage.remove(voucherId);
    }

    public void clear() {
        storage.clear();
    }
}
