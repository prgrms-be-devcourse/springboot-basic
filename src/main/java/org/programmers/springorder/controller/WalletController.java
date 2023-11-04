package org.programmers.springorder.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.Message;
import org.programmers.springorder.dto.wallet.WalletRequestDto;
import org.programmers.springorder.service.WalletService;
import org.programmers.springorder.utils.ExceptionHandler;
import org.programmers.springorder.utils.WalletMenuType;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WalletController {

    private final Console console;
    private final WalletService walletService;

    public WalletController(Console console, WalletService walletService) {
        this.console = console;
        this.walletService = walletService;
    }

    public void run() {
        WalletMenuType menu;
        do {
            menu = ExceptionHandler.input(console::inputWalletMenu);
            handleMenu(menu);
        } while (!menu.isBack());
    }

    private void handleMenu(WalletMenuType menu) {
        switch (menu) {
            case ASSIGN -> ExceptionHandler.process(WalletController::assignVoucher, this);
            case VOUCHER -> ExceptionHandler.process(WalletController::checkVoucher, this);
            case BACK -> console.back();
        }
    }

    private void assignVoucher() {
        WalletRequestDto request = console.inputWalletInfo();
        walletService.assignVoucher(request);
        console.printMessage(Message.VOUCHER_ASSIGNED);
    }

    private void checkVoucher() {
        UUID customerId = console.inputCustomerId();
        console.showVoucherList(walletService.getVoucher(customerId));
    }

}
