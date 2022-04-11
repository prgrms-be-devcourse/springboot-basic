package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findByAll( ) {
        Iterator<UUID> keys = storage.keySet().iterator();
        List<Voucher> voucherList = new ArrayList<>();
        while (keys.hasNext()) {
            voucherList.add(storage.get(keys.next()));
        }
        return voucherList;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
