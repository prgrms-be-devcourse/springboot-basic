package com.prgrms.springbasic.domain.voucher.controller;

import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.service.VoucherService;
import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.io.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
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
