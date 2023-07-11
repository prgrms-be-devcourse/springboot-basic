package com.programmers.springbootbasic.domain.voucher;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(UUID.randomUUID(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(storage.values());
    }

}
