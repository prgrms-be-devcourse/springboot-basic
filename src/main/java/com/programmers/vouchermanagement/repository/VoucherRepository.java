package com.programmers.vouchermanagement.repository;

import com.programmers.vouchermanagement.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    List<Voucher> findAll();

    Voucher save(Voucher voucher);

}
