package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID, Voucher> STORE = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        STORE.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(STORE.values());
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return Optional.of(STORE.get(voucherId));
    }

    @Override
    public void clear() {
        STORE.clear();
    }

}
