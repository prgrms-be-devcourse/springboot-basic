package com.devcourse.voucherapp.controller;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.service.VoucherService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public Voucher createVoucher(VoucherType voucherType, String discountAmount) {
        return voucherService.create(voucherType, discountAmount);
    }

    public Collection<Voucher> findAllVouchers() {
        return voucherService.findAllVouchers();
    }
}
