package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
    void deleteAll();
}
