package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.service.VoucherService;
import com.prgmrs.voucher.view.ConsoleViewVoucherCreationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class VoucherController {

    private VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public UUID createVoucher(long value, ConsoleViewVoucherCreationEnum type) {
        return voucherService.createVoucher(value, type);
    }

    public Map<UUID, Voucher> findAll() {
        return voucherService.findAll();
    }

    public Voucher findVoucherById(UUID uuid) {
        return voucherService.findVoucherById(uuid);
    }
}
