package org.programmers.springorder.voucher.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.utils.ExceptionHandler;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.service.VoucherService;
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
        VoucherRequestDto request = ExceptionHandler.input(Console::inputVoucherInfo);
        voucherService.save(request);
        console.printMessage(Message.VOUCHER_REGISTERED);
    }
}
