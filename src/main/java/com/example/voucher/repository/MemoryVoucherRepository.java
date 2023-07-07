package com.example.voucher.repository;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.example.voucher.domain.Voucher;

@Component
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> inMemoryDataBase = new ConcurrentHashMap<>();

    @Override
    public UUID save(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        inMemoryDataBase.put(voucherId, voucher);

        return inMemoryDataBase.get(voucherId).getVoucherId();

    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(inMemoryDataBase.values()));

    }

}
