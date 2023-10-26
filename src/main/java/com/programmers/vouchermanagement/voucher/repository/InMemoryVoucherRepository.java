package com.programmers.vouchermanagement.voucher.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

@Repository
@Profile("dev")
public class InMemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers;

    public InMemoryVoucherRepository() {
        vouchers = new HashMap<>();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    @Profile("test")
    public void deleteAll() {
        vouchers.clear();
    }
}
