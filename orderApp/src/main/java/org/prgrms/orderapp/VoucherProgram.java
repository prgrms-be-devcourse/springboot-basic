package org.prgrms.orderapp;

import org.prgrms.orderapp.io.Console;
import org.prgrms.orderapp.model.Voucher;
import org.prgrms.orderapp.model.VoucherType;
import org.prgrms.orderapp.service.VoucherService;

import java.util.Optional;

import static org.prgrms.orderapp.Utils.parseLong;

public class VoucherProgram implements Runnable {

    private final String CREATE = "create";
    private final String LIST = "list";
    private final String EXIT = "exit";
    private final VoucherService voucherService;
    private final Console console;

    public VoucherProgram(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void run() {
        String prompt = "=== Voucher Program ===\n" +
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers.\n";
        String input;
        while (true) {
            input = console.input(prompt);
            switch (input) {
                case CREATE -> createVoucher();
                case LIST -> console.vouchers(voucherService.getAllVoucher());
                case EXIT -> {
                    console.printMessage("Exiting the program.");
                    return;
                }
                default -> console.inputError(input);
            }
            System.out.println("====================================");
            prompt = "Please type a command : ";
        }
    }

    private void createVoucher() {
        String type = console.input("Creating a new voucher. Which type do you want? (fixed or percent) : ");
        if (!VoucherType.contains(type)) {
            console.inputError(type);
            return;
        }
        String value = console.input("How much discount? (digits only) : ");
        Optional<Voucher> voucher = voucherService.createVoucher(type, parseLong(value));
        if (voucher.isPresent()) {
            voucherService.saveVoucher(voucher.get());
            console.printMessage("Successfully created a voucher");
        } else {
            console.printMessage("Fail to create a voucher. Please provide appropriate types and values");
        }
    }

}
