package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.voucher.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TestVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void remove(UUID voucherId) {
        storage.remove(voucherId);
    }

    public void clear() {
        storage.clear();
    }

}
