package com.programmers.voucher.repository;

import com.programmers.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
