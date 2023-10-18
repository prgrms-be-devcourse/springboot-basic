package org.programmers.springorder.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.dto.VoucherRequestDto;
import org.programmers.springorder.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final Console console;

    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void getVoucherList() {
        console.showList(voucherService.getAllVoucher());
    }

    public void createVoucher() {
        VoucherRequestDto request = console.inputVoucherInfo();
        voucherService.save(request);
        console.printMessage(Message.VOUCHER_REGISTERED);
    }
}
