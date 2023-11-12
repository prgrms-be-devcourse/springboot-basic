package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;

@Profile("console")
@Controller
public class VoucherConsoleController {
    private final VoucherService voucherService;

    public VoucherConsoleController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    public List<VoucherResponse> readAll() {
        return voucherService.readAll();
    }
}
