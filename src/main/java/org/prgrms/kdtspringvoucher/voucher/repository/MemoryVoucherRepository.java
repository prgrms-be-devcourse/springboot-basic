package org.prgrms.kdtspringvoucher.voucher.repository;

import org.prgrms.kdtspringvoucher.voucher.service.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher findById(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(storage.get(voucherId));
        if (voucher.isEmpty()) {
            return null;
        }
        return voucher.get();
    }

    @Override
    public List<Voucher> findAll() {
        if (storage.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return findById(voucher.getVoucherId());
    }
}
