package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;

public interface VoucherService {
    Voucher addVoucher(Voucher voucher) throws IllegalArgumentException;
}
