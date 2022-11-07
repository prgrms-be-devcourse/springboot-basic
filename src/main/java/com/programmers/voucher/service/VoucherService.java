package com.programmers.voucher.service;

import com.programmers.voucher.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher register(Voucher voucher);

    Voucher getVoucher(UUID voucherId);

    List<Voucher> findAll();
}
