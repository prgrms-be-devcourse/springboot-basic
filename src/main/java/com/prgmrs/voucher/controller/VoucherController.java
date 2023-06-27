package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private VoucherService voucherService;
    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public UUID createFixedAmountVoucher(long value) {
        return voucherService.createFixedAmountVoucher(value);
    }

    public UUID createPercentDiscountVoucher(long value) {
        return voucherService.createPercentDiscountVoucher(value);
    }

    public Map<UUID, Voucher> findAll() {
        return voucherService.findAll();
    }

    public Voucher findVoucherById(UUID uuid) {
        return voucherService.findVoucherById(uuid);
    }
}
