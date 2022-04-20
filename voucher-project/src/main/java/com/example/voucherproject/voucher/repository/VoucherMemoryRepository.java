package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.voucher.domain.Voucher;

import java.util.*;

public class VoucherMemoryRepository implements VoucherRepository{
    private final Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        voucherMap.put(voucher.getId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findHavingTypeAll(VoucherType type) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public long count() {
        return 0;
    }
}
