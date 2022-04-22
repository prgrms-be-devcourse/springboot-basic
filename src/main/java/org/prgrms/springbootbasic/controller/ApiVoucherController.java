package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.dto.FindAllVoucherResponse;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiVoucherController {

    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public FindAllVoucherResponse findAllVouchers() {
        var voucherDTOS = voucherService.findAll();
        return new FindAllVoucherResponse(voucherDTOS);
    }
}
