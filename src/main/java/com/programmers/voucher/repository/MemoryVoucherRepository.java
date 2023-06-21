package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return Optional.of(store.get(voucherId));
    }
}
