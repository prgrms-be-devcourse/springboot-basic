package com.demo.voucher.service;

import com.demo.voucher.domain.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherService {
    Voucher getVoucher(UUID voucherId);

    Map<UUID, Voucher> findAllVouchers();

    Voucher createVoucher(Voucher voucher);
}
