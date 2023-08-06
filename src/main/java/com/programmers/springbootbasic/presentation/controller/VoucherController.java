package com.programmers.springbootbasic.presentation.controller;

import com.programmers.springbootbasic.common.util.Validator;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.presentation.controller.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.VoucherService;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public UUID createVoucher(String voucherType, Integer amountOrPercent) {
        checkValid(voucherType, amountOrPercent);
        VoucherCreationRequest request = new VoucherCreationRequest(VoucherType.from(voucherType), amountOrPercent);
        return voucherService.createVoucher(request);
    }

    private void checkValid(String voucherType, Integer amountOrPercent) {
        Validator.checkNullOrBlank(voucherType);
        Validator.checkNullNumber(amountOrPercent);
    }

    public VoucherResponses listVouchers() {
        return voucherService.list();
    }
}
