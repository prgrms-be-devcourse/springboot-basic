package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(CreateVoucherRequest createVoucherRequest) {
        return voucherService.createVoucher(createVoucherRequest);
    }

    public List<VoucherResponse> readAllVouchers() {
        return voucherService.readAllVouchers();
    }
}
