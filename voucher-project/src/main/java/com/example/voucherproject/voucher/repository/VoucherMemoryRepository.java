package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.voucher.domain.Voucher;

import java.util.*;

public class VoucherMemoryRepository implements VoucherRepository{
    private final Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMap.put(voucher.getId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getList() {
        return new ArrayList<>(voucherMap.values());
    }
}
