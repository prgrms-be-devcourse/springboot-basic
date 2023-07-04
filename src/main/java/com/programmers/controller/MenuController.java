package com.programmers.controller;

import com.programmers.domain.*;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import com.programmers.io.Console;
import com.programmers.service.BlacklistService;
import com.programmers.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    private static final String CREATE_VOUCHER_NUMBER = "1";
    private static final String CREATE_CUSTOMER_NUMBER = "2";

    private final Console console;
    private final VoucherService voucherService;
    private final BlacklistService blacklistService;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuController(Console console, VoucherService voucherService, BlacklistService blacklistService, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherService = voucherService;
        this.blacklistService = blacklistService;
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
                case LIST -> getVoucherList();
                case BLACKLIST -> getBlacklist();
            }
        }
    }

    public void create() {
        console.printCreateMessage();
        String command = console.readInput();
        checkCreateMenuSelection(command);

        switch (command) {
            case CREATE_VOUCHER_NUMBER -> voucherController.createVoucher();
            case CREATE_CUSTOMER_NUMBER -> customerController.createCustomer();
        }
    }

    private void checkCreateMenuSelection(String input) {
        if (!input.equals(CREATE_VOUCHER_NUMBER) && !input.equals(CREATE_CUSTOMER_NUMBER)) {
            throw new IllegalArgumentException();
        }
    }

    public void getVoucherList() {
        console.printVoucherListTitle();
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();
        console.printVouchers(vouchersResponseDto);
        log.info("The voucher list has been printed.");
    }

    public List<String> getBlacklist() {
        console.printBlacklistTitle();
        List<String> blacklist = blacklistService.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }
}
