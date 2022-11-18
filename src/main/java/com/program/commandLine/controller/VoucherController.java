package com.program.commandLine.controller;

import com.program.commandLine.io.Console;
import com.program.commandLine.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component(value = "voucherController")
public class VoucherController {

    private final VoucherService voucherService;
    private final Console console;

    public VoucherController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    public void createVoucher() {
        String voucherType = console.input("생성할 바우처 타입을 입력하세요?   1.Fixed Amount   2.percent discount : ");
        String discount = console.input("바우처의 할인 금액(혹은 퍼센트)을 입력하세요 : ");

        voucherService.createVoucher(voucherType, UUID.randomUUID(), Integer.parseInt(discount));
        console.successMessageView("바우처가 정상적으로 생성되었습니다. ");
    }

    public void lookupAllVoucherList() {
        console.voucherListView(voucherService.getAllVoucher());
    }

}
