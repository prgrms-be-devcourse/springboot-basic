package org.prgrms.java.repository.voucher;

import org.prgrms.java.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Collection<Voucher> findAll() {
        return storage.values();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }
}
