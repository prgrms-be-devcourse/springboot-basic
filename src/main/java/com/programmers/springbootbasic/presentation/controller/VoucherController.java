package com.programmers.springbootbasic.presentation.controller;

import com.programmers.springbootbasic.service.VoucherService;
import com.programmers.springbootbasic.service.dto.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.VoucherResponse;
import com.programmers.springbootbasic.service.dto.VoucherResponses;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(VoucherCreationRequest request) {
        return voucherService.createVoucher(request);
    }

    public VoucherResponses listVouchers() {
        return voucherService.list();
    }
}
