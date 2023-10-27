package org.programmers.springorder.voucher.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.voucher.dto.GiveVoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

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

    public void giveVoucher(){
        GiveVoucherRequestDto requestDto = console.giveVoucherInfo();
        voucherService.update(requestDto.getVoucherId(), requestDto.getCustomerId());
        console.printMessage(Message.VOUCHER_ALLOCATED);
    }

    public void getVoucherOfOwner(){
        UUID uuid = console.getCustomerId();
        console.showList(voucherService.getCustomerOwnedVouchers(uuid));
    }

    public void deleteVoucher(){
        UUID uuid = console.getVoucherId();
        voucherService.deleteVoucher(uuid);
        console.printMessage(Message.VOUCHER_DELETE_SUCCESS);
    }
}
