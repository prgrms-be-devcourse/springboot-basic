package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void clearStorage() {
        storage.clear();
    }
}
