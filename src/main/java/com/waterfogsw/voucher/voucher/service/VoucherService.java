package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher saveVoucher(Voucher voucher);

    List<Voucher> findAllVoucher();
}
