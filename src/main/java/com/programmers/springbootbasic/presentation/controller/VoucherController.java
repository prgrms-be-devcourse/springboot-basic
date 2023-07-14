package com.programmers.springbootbasic.presentation.controller;

import com.programmers.springbootbasic.common.util.Validator;
import com.programmers.springbootbasic.service.VoucherService;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(String voucherType, String name, Long minimumPriceCondition, LocalDateTime expiredAt, Integer amountOrPercent) {
        checkValid(voucherType, name, expiredAt, amountOrPercent);
        VoucherCreationRequest request = new VoucherCreationRequest(voucherType, name, minimumPriceCondition, expiredAt, amountOrPercent);
        return voucherService.createVoucher(request);
    }

    public VoucherResponses listVouchers() {
        return voucherService.list();
    }


    private void checkValid(String voucherType, String name, LocalDateTime expiredAt, Integer amountOrPercent) {
        Validator.checkNullOrBlank(voucherType);
        Validator.checkNullOrBlank(name);
        Validator.checkNullDateTime(expiredAt);
        Validator.checkNullNumber(amountOrPercent);
    }
}
