package com.demo.voucher.controller;

import com.demo.voucher.domain.Voucher;
import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public Voucher createVoucher(VoucherType voucherType, String amount) {
        return voucherService.createVoucher(voucherType, amount);
    }

    public List<Voucher> getAllVouchers() {
        return voucherService.findAllVouchers();
    }

}
