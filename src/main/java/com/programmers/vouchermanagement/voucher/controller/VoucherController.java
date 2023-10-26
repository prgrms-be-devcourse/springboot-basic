package com.programmers.vouchermanagement.voucher.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    public List<VoucherResponse> readAllVouchers() {
        return voucherService.readAllVouchers();
    }
}
