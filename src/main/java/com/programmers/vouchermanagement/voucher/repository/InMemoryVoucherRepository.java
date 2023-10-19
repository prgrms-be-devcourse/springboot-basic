package com.programmers.vouchermanagement.voucher.repository;

import java.util.*;

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
    public void save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }
}
