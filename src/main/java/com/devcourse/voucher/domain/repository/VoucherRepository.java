package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
