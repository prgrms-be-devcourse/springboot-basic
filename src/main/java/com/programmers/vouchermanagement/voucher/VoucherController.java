package com.programmers.vouchermanagement.voucher;

import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    //TODO: add DTO
    public Voucher create(Voucher voucher) {
        return voucherService.create(voucher);
    }
}
