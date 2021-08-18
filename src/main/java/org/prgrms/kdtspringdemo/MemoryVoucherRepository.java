package org.prgrms.kdtspringdemo;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private  final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private List<Voucher> vouchers = new ArrayList<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
//        this.vouchers.add(voucher);
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers;
    }
}
