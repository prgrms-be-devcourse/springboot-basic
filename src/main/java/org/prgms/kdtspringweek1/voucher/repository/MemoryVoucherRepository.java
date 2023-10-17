package org.prgms.kdtspringweek1.voucher.repository;

import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> allVouchers = new ArrayList<>(vouchers.values());
        return allVouchers;
    }
}
