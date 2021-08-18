package org.programmers.kdt.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("Memory")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher) {
        return this.repository.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> list = new ArrayList<>();

        Set<UUID> keys = this.repository.keySet();
        for (UUID key : keys) {
            list.add(this.repository.get(key));
        }

        return list;
    }
}
