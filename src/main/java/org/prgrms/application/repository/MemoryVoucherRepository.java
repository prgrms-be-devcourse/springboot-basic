package org.prgrms.application.repository;

import org.prgrms.application.domain.Voucher;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        List<Voucher> copiedVouchers = new ArrayList<>();
        for(Voucher voucher : storage.values()){
            copiedVouchers.add(voucher.copy());
        }
        return copiedVouchers;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
