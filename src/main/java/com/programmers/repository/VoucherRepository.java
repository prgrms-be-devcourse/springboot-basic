package com.programmers.repository;

import com.programmers.domain.Voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();
}
