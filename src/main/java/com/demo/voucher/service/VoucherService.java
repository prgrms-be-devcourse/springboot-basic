package com.demo.voucher.service;

import com.demo.voucher.domain.Voucher;

import java.util.UUID;

public interface VoucherService {
    Voucher getVoucher(UUID voucherId);

    Voucher createVoucher(Voucher voucher);
}
