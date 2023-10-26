package com.programmers.vouchermanagement.voucher.repository;

import java.util.List;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

public interface VoucherRepository {
    void save(Voucher voucher);
    List<Voucher> findAll();
}
