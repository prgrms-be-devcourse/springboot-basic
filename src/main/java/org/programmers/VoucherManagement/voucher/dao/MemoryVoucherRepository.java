package org.programmers.VoucherManagement.voucher.dao;

import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        map.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return map.values()
                .stream()
                .toList();
    }
}
