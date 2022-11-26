package com.program.commandLine.controller;

import com.program.commandLine.io.Console;
import com.program.commandLine.io.MenuType;
import com.program.commandLine.model.VoucherInputData;
import com.program.commandLine.service.VoucherService;
import org.springframework.stereotype.Component;

@Component(value = "voucherController")
public class VoucherController {

    private final VoucherWalletController voucherWalletController;
    private final VoucherService voucherService;
    private final Console console;

    private enum VoucherMenuType {
        CREATE, ALLOCATE, RETRIEVE, LIST, CUSTOMER
    }

    public VoucherController(VoucherWalletController voucherWalletController, VoucherService voucherService, Console console) {
        this.voucherWalletController = voucherWalletController;
        this.voucherService = voucherService;
        this.console = console;
    }

    public void createVoucher() {
        String voucherType = console.input("생성할 바우처 타입을 입력하세요?   1.Fixed Amount   2.percent discount : ");
        String discount = console.input("바우처의 할인 금액(혹은 퍼센트)을 입력하세요 : ");

        voucherService.createVoucher(new VoucherInputData(voucherType,discount));
        console.successMessageView("바우처가 정상적으로 생성되었습니다. ");
    }

    public void lookupAllVoucherList() {
        console.voucherListView(voucherService.getAllVoucher());
    }

    public void run(){
        console.menuView(MenuType.VOUCHER);
        String choseMenu = console.input();
        VoucherMenuType voucherMenuType = VoucherMenuType.valueOf(choseMenu.toUpperCase());
        switch (voucherMenuType) {
            case CREATE -> createVoucher();
            case ALLOCATE -> voucherWalletController.assignCustomer();
            case RETRIEVE -> voucherWalletController.retrieveVoucher();
            case LIST -> voucherWalletController.lookupAssignedVouchersByCustomer();
            case CUSTOMER -> voucherWalletController.lookupCustomerWithVoucher();
        }
    }
}
