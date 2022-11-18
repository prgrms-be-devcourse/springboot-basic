package com.program.commandLine.controller;

import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component(value = "voucherController")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createVoucher(String voucherType, int discount) {
        return voucherService.createVoucher(voucherType, UUID.randomUUID(), discount);
    }

    public List<Voucher> getVoucherList() {
        return voucherService.getAllVoucher();
    }


}
