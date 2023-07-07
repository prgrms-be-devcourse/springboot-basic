package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public class JdbcVoucherRepository implements VoucherRepository {

    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }
}
