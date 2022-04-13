package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
