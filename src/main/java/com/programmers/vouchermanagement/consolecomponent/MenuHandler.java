package com.programmers.vouchermanagement.consolecomponent;

import static com.programmers.vouchermanagement.constant.message.ExceptionMessage.INEXECUTABLE_MESSAGE;

import com.programmers.vouchermanagement.voucher.Voucher;
import com.programmers.vouchermanagement.voucher.VoucherController;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuHandler {
    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;

    public MenuHandler(ConsoleManager consoleManager, VoucherController voucherController) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
    }

    // Options..
    // ConsoleAppRunner (recursive run) --> MenuHandler --> Menu
    // ConsoleAppRunner --> Menu <-- MenuHandler
    // ConsoleAppRunner --> Menu

    public boolean handleMenu() {
        Menu menu = selectMenu();
        executeMenu(menu);
        return isValidMenu(menu);
    }

    private Menu selectMenu() {
        return consoleManager.selectMenu();
    }

    private boolean isValidMenu(Menu menu) {
        if (menu.isExit()) {
            return false;
        }

        if (menu.isIncorrect()) {
            throw new IllegalArgumentException(INEXECUTABLE_MESSAGE);
        }

        return true;
    }

    private void executeMenu(Menu menu) {
        switch (menu) {
            case EXIT -> consoleManager.printExit();
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
            case CREATE -> {
                Voucher voucher = consoleManager.instructCreate();
                voucher = voucherController.create(voucher);
                consoleManager.printCreateResult(voucher);
            }
            case LIST -> {
                List<Voucher> vouchers = voucherController.readAllVouchers();
                consoleManager.printReadAll(vouchers);
            }
        }
    }
}
