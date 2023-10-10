package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.voucher.Voucher;

import java.util.List;

public interface Repository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
