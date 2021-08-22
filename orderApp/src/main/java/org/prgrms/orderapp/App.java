package org.prgrms.orderapp;

import org.prgrms.orderapp.io.Console;
import org.prgrms.orderapp.service.VoucherService;

public class App implements Runnable {

    private final VoucherService voucherService;
    private final Console console;

    public App(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void run() {
        String prompt = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """;
        String input;
        while (true) {
            input = console.input(prompt);
            switch (input) {
                case "create" -> {
                    String type = console.input("Creating a new voucher. Which type do you want? (fixed or percent) : ");
                    if (!voucherService.checkValidity(type)) {
                        console.inputError(type);
                        break;
                    }
                    input = console.input("How much discount? (digits only) : ");
                    long value = parseLong(input);
                    if (!voucherService.checkValidity(type, value)) {
                        console.inputError(input);
                        break;
                    }
                    voucherService.saveVoucher(voucherService.createVoucher(type, value));
                    System.out.println("Successfully created a voucher");
                }
                case "list" -> {
                    System.out.println("Listing all vouchers created:");
                    console.vouchers(voucherService.getAllVoucher());
                }
                case "exit" -> {
                    System.out.println("Exiting the program.");
                    return;
                }
                default -> console.inputError(input);
            }
            System.out.println("====================================");
            prompt = "Please type a command : ";
        }
    }

    public long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
