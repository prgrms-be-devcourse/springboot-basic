package com.programmers.vouchermanagement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers;

    public InMemoryVoucherRepository() {
        vouchers = new HashMap<>();
    }

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }
}
