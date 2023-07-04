package com.programmers.controller;

import com.programmers.domain.*;
import com.programmers.io.Console;
import com.programmers.service.BlacklistService;
import com.programmers.service.VoucherService;
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
        log.info("The voucher program is activated.");

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
        if (!input.equals(MENU_VOUCHER_NUMBER) && !input.equals(MENU_CUSTOMER_NUMBER)) {
            throw new IllegalArgumentException();
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
}
