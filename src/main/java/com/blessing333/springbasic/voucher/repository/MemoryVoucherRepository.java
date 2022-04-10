package com.blessing333.springbasic.voucher.repository;

import com.blessing333.springbasic.voucher.domain.Voucher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID,Voucher> voucherStore = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(voucherStore.get(id));
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
