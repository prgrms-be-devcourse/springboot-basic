package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
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
    public void update(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Override
    public List<Voucher> findAllByCreatedAtAndVoucherType(LocalDateTime createdAt, VoucherType voucherType) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.getCreatedAt().isAfter(createdAt))
                .filter(voucher -> voucher.getVoucherType().equals(voucherType))
                .toList();
    }
}
