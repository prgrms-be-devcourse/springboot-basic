package com.programmers.voucher.repository;

import com.programmers.voucher.domain.voucher.Voucher;

import java.util.Map;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Map<String, Voucher> findAll();
}
