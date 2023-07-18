package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.dto.CreateVoucherRequest;
import org.prgrms.kdt.voucher.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void create(CreateVoucherRequest request) {
        voucherService.createVoucher(request);
    }

    public VoucherResponses findAll() {
        return voucherService.findAll();
    }
}
