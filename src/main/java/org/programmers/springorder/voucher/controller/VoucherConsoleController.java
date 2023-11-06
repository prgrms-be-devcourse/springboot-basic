package org.programmers.springorder.voucher.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.voucher.dto.GiveVoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Profile("test")
@Controller
public class VoucherConsoleController {
    private final Console console;

    private final VoucherService voucherService;

    public VoucherConsoleController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void getVoucherList() {
        console.showList(voucherService.getAllVoucher());
    }

    public void createVoucher() {
        VoucherRequestDto request = console.inputVoucherInfo();
        voucherService.saveNewVoucher(request);
        console.printMessage(Message.VOUCHER_REGISTERED);
    }

    public void giveVoucher(){
        GiveVoucherRequestDto requestDto = console.giveVoucherInfo();
        voucherService.allocateVoucher(requestDto.getVoucherId(), requestDto.getCustomerId());
        console.printMessage(Message.VOUCHER_ALLOCATED);
    }

    public void getVouchersOfOwner(){
        UUID uuid = console.getCustomerId();
        console.showList(voucherService.getCustomerOwnedVouchers(uuid));
    }

    public void deleteVoucher(){
        UUID uuid = console.getVoucherId();
        voucherService.deleteVoucher(uuid);
        console.printMessage(Message.VOUCHER_DELETE_SUCCESS);
    }
}
