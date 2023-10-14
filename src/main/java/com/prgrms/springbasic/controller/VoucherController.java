package com.prgrms.springbasic.controller;

import com.prgrms.springbasic.dto.CreateVoucherRequest;
import com.prgrms.springbasic.dto.VoucherResponse;
import com.prgrms.springbasic.io.Console;
import com.prgrms.springbasic.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final Console console;

    public VoucherController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    public VoucherResponse saveVoucher(CreateVoucherRequest request) {
        return voucherService.saveVoucher(request);
    }

    public void findAll() {
        List<VoucherResponse> vouchers = voucherService.findAll();
        console.printVouchers(vouchers);
    }
}
