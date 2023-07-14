package org.weekly.weekly.voucher.controller;

import org.springframework.stereotype.Controller;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;
import org.weekly.weekly.voucher.dto.response.VouchersResponse;
import org.weekly.weekly.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherCreationResponse createVoucher(VoucherCreationRequest voucherCreationRequest) {
        return voucherService.insertVoucher(voucherCreationRequest);
    }

    public VouchersResponse getVouchers() {
        return voucherService.getVouchers();
    }
}
