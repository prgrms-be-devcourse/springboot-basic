package com.example.voucher_manager.domain.repository;

import com.example.voucher_manager.domain.voucher.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        memory.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void clear() {
        memory.clear();
    }
}
