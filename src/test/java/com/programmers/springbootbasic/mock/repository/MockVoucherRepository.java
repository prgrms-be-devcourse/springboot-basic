package com.programmers.springbootbasic.mock.repository;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.repository.voucher.VoucherRepository;

import java.util.List;

public class MockVoucherRepository implements VoucherRepository {
    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
