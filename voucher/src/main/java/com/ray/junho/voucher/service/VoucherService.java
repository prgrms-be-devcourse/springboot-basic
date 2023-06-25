package com.ray.junho.voucher.service;

import com.ray.junho.voucher.repository.VoucherRepository;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
}
