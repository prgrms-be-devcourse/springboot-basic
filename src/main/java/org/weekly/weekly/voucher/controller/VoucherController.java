package org.weekly.weekly.voucher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;


    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public Response createVoucher(VoucherCreationRequest voucherCreationRequest) {
        return this.voucherService.insertVoucher(voucherCreationRequest);
    }

    public Response getVouchers() {
        return this.voucherService.getVouchers();
    }
}
