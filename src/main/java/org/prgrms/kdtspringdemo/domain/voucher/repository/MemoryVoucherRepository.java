package org.prgrms.kdtspringdemo.domain.voucher.repository;

import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAllVaucher() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }
}
