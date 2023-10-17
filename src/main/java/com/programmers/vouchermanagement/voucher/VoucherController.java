package com.programmers.vouchermanagement.voucher;

import org.springframework.stereotype.Controller;

import java.util.List;

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

    public List<Voucher> readAllVouchers() {
        try {
            return voucherService.readAllVouchers();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
