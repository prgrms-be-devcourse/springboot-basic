package com.programmers.springbootbasic.presentation.controller;

import com.programmers.springbootbasic.common.util.Validator;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.service.VoucherService;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(String voucherType, Integer amountOrPercent) {
        checkValid(voucherType, amountOrPercent);
        VoucherCreationRequest request = new VoucherCreationRequest(VoucherType.from(voucherType), amountOrPercent);
        return voucherService.createVoucher(request);
    }

    public VoucherResponses listVouchers() {
        return voucherService.list();
    }


    private void checkValid(String voucherType, Integer amountOrPercent) {
        Validator.checkNullOrBlank(voucherType);
        Validator.checkNullNumber(amountOrPercent);
    }
}
