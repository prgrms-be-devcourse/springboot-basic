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
        boolean isRunning = true;

        while (isRunning) {
            WalletMenuType menu = ExceptionHandler.input(Console::inputWalletMenu);

            switch (menu) {
                case ASSIGN -> ExceptionHandler.process(WalletController::assignVoucher, this);
                case VOUCHER -> ExceptionHandler.process(WalletController::checkVoucher, this);
//                case DELETE -> deleteVoucher();
//                case CUSTOMER -> checkCusomter();
                case BACK -> {
                    isRunning = false;
                    console.printMessage(Message.BACK_TO_MENU_MESSAGE);
                }
            }
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

    private void deleteVoucher() {  // TODO: 시간 관계상 리뷰 받고 구현하기

    }

    private void checkCusomter() {  // TODO: 시간 관계상 리뷰 받고 구현하기

    }

}
