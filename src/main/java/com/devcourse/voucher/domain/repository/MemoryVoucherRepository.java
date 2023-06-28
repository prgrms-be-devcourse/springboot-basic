package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> memoryStorage = new ConcurrentHashMap<>();

    public MemoryVoucherRepository() {
    }

    @Override
    public Voucher save(Voucher voucher) {
        memoryStorage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(memoryStorage.values());
    }
}
