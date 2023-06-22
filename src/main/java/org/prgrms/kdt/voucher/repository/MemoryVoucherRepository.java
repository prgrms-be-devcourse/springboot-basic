package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();
    @Override
    public Voucher insert(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

}
