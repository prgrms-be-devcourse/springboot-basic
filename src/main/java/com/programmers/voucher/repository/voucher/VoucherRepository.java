package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
