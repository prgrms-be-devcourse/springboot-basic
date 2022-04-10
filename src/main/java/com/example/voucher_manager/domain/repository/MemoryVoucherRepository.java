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
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            memory.put(voucher.getVoucherId(), voucher);
        } catch (RuntimeException e){
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    public void clear() {
        memory.clear();
    }
}
