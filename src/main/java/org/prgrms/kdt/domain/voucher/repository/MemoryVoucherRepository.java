package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public UUID save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByCustomerId(UUID voucherId) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(storage.values().stream().toList());
    }

    @Override
    public int updateById(Voucher voucher) {
        return 0;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }
}
