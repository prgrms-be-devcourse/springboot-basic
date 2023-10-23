package org.prgrms.vouchermanager.repository;

import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findByID(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> all = new ArrayList<>(storage.values());
        return all;
    }
}
