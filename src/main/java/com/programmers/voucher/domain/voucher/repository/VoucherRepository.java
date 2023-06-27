package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
