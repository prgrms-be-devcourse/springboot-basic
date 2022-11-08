package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
