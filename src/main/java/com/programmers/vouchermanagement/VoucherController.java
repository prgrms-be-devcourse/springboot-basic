package com.programmers.vouchermanagement;

import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher create(Voucher voucher) {
        return voucherService.create(voucher);
    }
}
