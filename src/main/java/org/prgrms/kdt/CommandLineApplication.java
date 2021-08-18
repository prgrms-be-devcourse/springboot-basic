package org.prgrms.kdt;

import org.prgrms.kdt.core.*;
import org.prgrms.kdt.service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    private static final Input input = new Console();
    private static final Output output = new Console();

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        start();
        String inputStr;
        while (true) {
            inputStr = input.input().toUpperCase();
            switch (inputStr) {
                case "EXIT" -> {
                    exit();
                    return;
                }
                case "CREATE" -> createVoucher(voucherService);
                case "LIST" -> listVoucher(voucherService);
                default -> output.inputError(inputStr);
            }
        }

    }

    private static void listVoucher(VoucherService voucherService) {
    }

    private static void createVoucher(VoucherService voucherService) {
        String type = input.input(
            "Creating a new voucher. Which type do you want? (fixed or percent) : "
        ).toUpperCase();
        if (type.contains("fixed") || type.contains("percent")) {
            long value = Long.parseLong(input.input("How much discount? (digits only) : "));
            voucherService.createVoucher(type, value);
        } else {
            output.inputError(type);
        }
    }


    private static void start() {
        var prompt = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";
        output.printMessage(prompt);
    }

    private static void exit() {
        output.printMessage("BYE!");
    }
}
