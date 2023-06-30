package org.prgrms.application.controller;

import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public List<Voucher> getStorage() {
        return voucherService.getVoucherList();
    }

    public void createVoucher(VoucherType voucherType, double voucherDetail) {
        voucherService.createVoucher(voucherType, voucherDetail);
    }
}