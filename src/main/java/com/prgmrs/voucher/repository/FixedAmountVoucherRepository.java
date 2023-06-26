package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class FixedAmountVoucherRepository implements VoucherRepository {
    Map<UUID, Voucher> history = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        history.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return history;
    }
}
