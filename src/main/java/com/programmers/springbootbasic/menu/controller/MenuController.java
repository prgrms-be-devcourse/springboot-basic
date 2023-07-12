package com.programmers.springbootbasic.menu.controller;

import com.programmers.springbootbasic.customer.controller.CustomerController;
import com.programmers.springbootbasic.exception.InvalidRequestValueException;
import com.programmers.springbootbasic.io.Console;
import com.programmers.springbootbasic.menu.domain.Menu;
import com.programmers.springbootbasic.voucher.controller.VoucherController;
import com.programmers.springbootbasic.wallet.controller.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    private static final String MENU_VOUCHER_NUMBER = "1";
    private static final String MENU_CUSTOMER_NUMBER = "2";

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public MenuController(Console console, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    public void run() {
        boolean activated = true;
        log.info("The program is activated.");

        while (activated) {
            console.printMenu();
            String command = console.readInput();
            Menu menu = Menu.findMenu(command);

            switch (menu) {
                case EXIT -> {
                    activated = false;
                    log.info("The program has been terminated.");
                }
                case CREATE -> create();
                case LIST -> list();
                case UPDATE -> update();
                case DELETE -> delete();
                case WALLET -> walletController.activate();
            }
        }
    }

    public void create() {
        console.printCreateMessage();
        String command = console.readInput();
        checkMenuSelection(command);

        switch (command) {
            case MENU_VOUCHER_NUMBER -> voucherController.createVoucher();
            case MENU_CUSTOMER_NUMBER -> customerController.createCustomer();
        }
    }

    private void checkMenuSelection(String selection) {
        if (selection.isEmpty()) {
            throw new InvalidRequestValueException("[ERROR] 메뉴 번호가 비었습니다.");
        }

        if (!selection.equals(MENU_VOUCHER_NUMBER) && !selection.equals(MENU_CUSTOMER_NUMBER)) {
            throw new InvalidRequestValueException("[ERROR] 메뉴 번호가 유효하지 않습니다.");
        }
    }

    public void list() {
        console.printListMessage();
        String command = console.readInput();
        checkMenuSelection(command);

        switch (command) {
            case MENU_VOUCHER_NUMBER -> voucherController.getVoucherList();
            case MENU_CUSTOMER_NUMBER -> customerController.getCustomerList();
        }
    }

    public void update() {
        console.printUpdateMessage();
        String command = console.readInput();
        checkMenuSelection(command);

        switch (command) {
            case MENU_VOUCHER_NUMBER -> voucherController.updateVoucher();
            case MENU_CUSTOMER_NUMBER -> customerController.updateCustomer();
        }
    }

    public void delete() {
        console.printDeleteMessage();
        String command = console.readInput();
        checkMenuSelection(command);

        switch (command) {
            case MENU_VOUCHER_NUMBER -> voucherController.deleteVoucher();
            case MENU_CUSTOMER_NUMBER -> customerController.deleteCustomer();
        }
    }
}
