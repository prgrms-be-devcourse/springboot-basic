package com.programmers.repository.voucher;

import com.programmers.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
