package org.prgrms.springbootbasic.repository;


import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("default")
public class InMemoryVoucherRepository implements VoucherRepository {
    Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public void insert(Voucher voucher) {
        map.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (!map.containsKey(voucherId)) {
            return Optional.empty();
        }
        return Optional.of(map.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(map.values());
    }
}
