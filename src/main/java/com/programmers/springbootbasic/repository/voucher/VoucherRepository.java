package com.programmers.springbootbasic.repository.voucher;

import com.programmers.springbootbasic.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
