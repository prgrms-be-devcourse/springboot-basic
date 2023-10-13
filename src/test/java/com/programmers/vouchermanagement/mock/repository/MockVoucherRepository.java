package com.programmers.vouchermanagement.mock.repository;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;

import java.util.*;

public class MockVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new HashMap<>();

    public MockVoucherRepository(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> storage.put(voucher.getId(), voucher));
    }

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
