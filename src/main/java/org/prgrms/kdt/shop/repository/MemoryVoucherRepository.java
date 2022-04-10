package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> getStorage( ) {
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
