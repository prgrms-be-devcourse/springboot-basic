package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoucherMemoryRepository implements VoucherRepository{
    private final Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return store.values()
                .stream()
                .toList();
    }
}
