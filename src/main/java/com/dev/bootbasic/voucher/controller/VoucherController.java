package com.dev.bootbasic.voucher.controller;

import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import com.dev.bootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        return voucherService.createVoucher(request);
    }

    public List<VoucherDetailsResponse> findAllVouchers() {
        return voucherService.findAllVouchers();
    }

}
