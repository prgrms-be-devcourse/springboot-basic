package org.prgrms.kdtspringdemo.controller;

import org.prgrms.kdtspringdemo.domain.voucher.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherType;
import org.prgrms.kdtspringdemo.service.VoucherService;

import java.util.List;

public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher save(VoucherType voucherType, long discountAmount) {
        return null;
    }

    public List<Voucher> findAll() {
        return null;
    }
}
