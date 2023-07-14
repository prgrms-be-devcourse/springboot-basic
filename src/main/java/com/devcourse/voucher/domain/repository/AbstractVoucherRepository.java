package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;

import java.util.List;

class AbstractVoucherRepository implements VoucherRepository {
    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
