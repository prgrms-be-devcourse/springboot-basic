package com.blessing333.springbasic.component.voucher.repository;

import com.blessing333.springbasic.component.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(voucherStore.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return voucherStore.values().stream().toList();
    }

    @Override
    public void save(Voucher voucher) {
        voucherStore.put(voucher.getVoucherId(),voucher);
    }

    @Override
    public void delete(UUID uuid) {
        voucherStore.remove(uuid);
    }
}
