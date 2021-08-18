package org.prgms;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID,Voucher> storage = new ConcurrentHashMap<>(); // thread safety ConcurrentHashMap 사용

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId)); // null일 경우 empty
    }

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
