package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getVoucherList() {
        return new ArrayList<>(storage.values());
    }
}
