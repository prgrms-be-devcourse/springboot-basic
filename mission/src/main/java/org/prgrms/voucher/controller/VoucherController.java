package org.prgrms.voucher.controller;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {

        this.voucherService = voucherService;
    }

    public Voucher create(Voucher voucher) {

        return null;
    }
}
