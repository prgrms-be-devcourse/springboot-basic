package com.prgmrs.voucher.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleService {

    private Scanner sc;
    private VoucherService voucherService;

    public ConsoleService(Scanner sc, VoucherService voucherService) {
        this.sc = sc;
        this.voucherService = voucherService;
    }

    public String read() {
        return sc.nextLine();
    }

    public void write(String message) {
        System.out.println(message);
    }

    public void run() {
        boolean continueRunning = true;
        while(continueRunning) {
            showCommand();
            ConsoleServiceEnum consoleServiceEnum = ConsoleServiceEnum.findByCommand(read());
            switch(consoleServiceEnum) {
                case EXIT_THE_LOOP:
                    continueRunning = false;
                    break;
                case CREATE_THE_VOUCHER:
                    selectVoucher();
                    break;
                case SHOW_THE_LIST:
                    break;
                default:
                    write("Incorrect command typed.");
                    break;
            }
        }
    }

    public void showCommand() {
        write("=== Voucher Program ===");
        write("Type exit to exit the program.");
        write("Type create to create a new voucher.");
        write("Type list to list all vouchers.");
    }

    public void showVoucher() {
        write("=== Creating Voucher ===");
        write("Type fixed to create a voucher with fixed amount.");
        write("Type percent to create a voucher with percent discount.");
    }

    private void selectVoucher() {
        boolean continueRunning = true;
        long value;
        while(continueRunning) {
            showVoucher();
            ConsoleServiceEnum consoleServiceEnum = ConsoleServiceEnum.findByCommand(read());
            switch(consoleServiceEnum) {
                case CREATE_FIXED_AMOUNT_VOUCHER:
                    write("=== Creating Voucher with fixed amount ===");
                    write("Type amount to create a voucher with fixed amount.");
                    value = Long.parseLong(read());
//                    voucherService.createFixedAmountVoucher(value);
                    write("=== Successfully created a new voucher ===");

                    continueRunning = false;
                    break;
                case CREATE_PERCENT_DISCOUNT_VOUCHER:
                    write("=== Creating Voucher with percent discount ===");
                    write("Type percent to create a voucher with percent discount.");
                    value = Long.parseLong(read());
//                    voucherService.createPercentDiscountVoucher(value);
                    write("=== Successfully created a new voucher ===");
                    continueRunning = false;
                    break;
                default:
                    write("Incorrect command typed.");
                    break;
            }
        }
    }

}
