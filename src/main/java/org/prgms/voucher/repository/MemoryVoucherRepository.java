package org.prgms.voucher.repository;

import org.prgms.voucher.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Voucher save(Voucher voucher) {
        store.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
}
