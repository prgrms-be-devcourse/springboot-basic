package com.zerozae.voucher.mock;

import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.repository.voucher.VoucherRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers = new HashMap<>();
    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }
    @Override
    public List<Voucher> findAllVouchers() {
        return vouchers.values().stream().toList();
    }
}
