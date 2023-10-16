package com.programmers.vouchermanagement.repository;


import com.programmers.vouchermanagement.domain.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class MemVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Voucher save(Voucher voucher) {
        return vouchers.put(voucher.getId(), voucher);
    }
}
