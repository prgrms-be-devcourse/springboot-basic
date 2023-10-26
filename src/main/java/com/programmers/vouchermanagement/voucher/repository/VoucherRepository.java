package com.programmers.vouchermanagement.voucher.repository;

import java.util.List;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
    void deleteAll();
}
