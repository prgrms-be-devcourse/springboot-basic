package com.devcourse.voucher.repository;

import com.devcourse.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
