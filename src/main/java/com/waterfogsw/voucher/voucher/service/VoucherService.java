package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherType type, Double value);

    List<Voucher> findAll();
}
