package com.programmers.controller;

import com.programmers.domain.*;
import com.programmers.exception.EmptyException;
import com.programmers.exception.InvalidInputException;
import com.programmers.io.Console;
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

    public MenuController(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
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

    private void checkMenuSelection(String input) {
        if (input.isEmpty()) {
            throw new EmptyException("[ERROR] 메뉴 번호가 입력되지 않았습니다.");
        }

        if (!input.equals(MENU_VOUCHER_NUMBER) && !input.equals(MENU_CUSTOMER_NUMBER)) {
            throw new InvalidInputException("[ERROR] 입력하신 메뉴 번호가 유효하지 않습니다.");
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
