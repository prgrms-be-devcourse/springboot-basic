package com.programmers.voucher.repository;

import com.programmers.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
