package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
