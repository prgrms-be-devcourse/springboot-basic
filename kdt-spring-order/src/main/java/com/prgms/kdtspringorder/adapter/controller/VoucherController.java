package com.prgms.kdtspringorder.adapter.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.prgms.kdtspringorder.application.VoucherService;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createVoucher(VoucherType voucherType, long discount) {
        return voucherService.create(UUID.randomUUID(), voucherType, discount);
    }

    public Map<UUID, Voucher> listVouchers() {
        return voucherService.findAll();
    }
}
