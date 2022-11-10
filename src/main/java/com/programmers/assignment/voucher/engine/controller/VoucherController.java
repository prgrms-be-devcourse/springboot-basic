package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.service.VoucherService;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;

public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public void makeVoucher(String discountWay) {
        if (discountWay.equals(VoucherVariable.PERCENT.toString())){
            voucherService.makePercentVoucher();
            return;
        }

        if (discountWay.equals(VoucherVariable.FIXED.toString())){
            voucherService.makeFixedVoucher();
            return;
        }
        throw new IllegalArgumentException(discountWay + "는 존재하지 않는 할인 방법 입니다.");
    }


}
