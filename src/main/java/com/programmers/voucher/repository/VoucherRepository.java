package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}
