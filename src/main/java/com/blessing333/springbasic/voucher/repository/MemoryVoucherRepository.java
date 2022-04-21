package com.blessing333.springbasic.voucher.repository;

import com.blessing333.springbasic.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("test")
public class MemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public void update(Voucher voucher) {
        voucherStore.remove(voucher.getVoucherId());
        insert(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(voucherStore.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return voucherStore.values().stream().toList();
    }

    @Override
    public void insert(Voucher voucher) {
        voucherStore.put(voucher.getVoucherId(),voucher);
    }

    @Override
    public void deleteAll() {
        voucherStore.clear();
    }
}
