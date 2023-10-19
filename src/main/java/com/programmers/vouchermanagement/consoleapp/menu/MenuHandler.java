package com.programmers.vouchermanagement.consoleapp.menu;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequestDTO;
import com.programmers.vouchermanagement.voucher.controller.VoucherController;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDTO;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuHandler {
    //messages
    private static final String INCORRECT_MESSAGE =
            "This menu is not executable.";
    //---

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

        try {
            executeMenu(menu);
        } catch (RuntimeException e) {
            consoleManager.printException(e);
        }

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
            throw new IllegalArgumentException(INCORRECT_MESSAGE);
        }

        return true;
    }

    private void executeMenu(Menu menu) {
        switch (menu) {
            case EXIT -> consoleManager.printExit();
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
            case CREATE -> {
                CreateVoucherRequestDTO createVoucherRequestDTO = consoleManager.instructCreate();
                VoucherResponseDTO voucherResponse = voucherController.create(createVoucherRequestDTO);
                consoleManager.printCreateResult(voucherResponse);
            }
            case LIST -> {
                List<VoucherResponseDTO> voucherResponses = voucherController.readAllVouchers();
                consoleManager.printReadAll(voucherResponses);
            }
        }
    }
}
