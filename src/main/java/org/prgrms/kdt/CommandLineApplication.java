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
        var prompt = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";

        String inputStr;
        while (true) {
            inputStr = input.input(prompt).toUpperCase();
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

        var vouchers = voucherService.listVoucher();
        if(vouchers.isEmpty()) {
            output.printMessage("No Voucher Data");
        } else {
            output.printMessage("=== Voucher List ===");
            vouchers.forEach(v-> output.printMessage(v.toString()));
        }
    }

    private static void createVoucher(VoucherService voucherService) {
        String type = input.input(
            "Creating a new voucher. Choose a voucher type (fixed or percent): "
        );
        if (type.equals("fixed") || type.equals("percent")) {
            var value = Long.parseLong(input.input("How much discount?: "));
            voucherService.createVoucher(type, value);
            output.printMessage("create success!");
        } else {
            output.inputError(type);
        }
    }

    private static void exit() {
        output.printMessage("BYE!");
    }
}
