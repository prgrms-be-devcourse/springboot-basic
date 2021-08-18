package org.programmers.kdt;

import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;
import org.programmers.kdt.utils.MyUtils;
import org.programmers.kdt.voucher.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

public class CommandLineApplication implements Runnable {
    private Input input;
    private Output output;

    private final String welcomeMessage
            = "\n=== Voucher Program ===\nType create to create a voucher.\nType list to list all vouchers.\nType exit to exit the program.";
    VoucherFactory voucherFactory;

    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        do {
            String command = input.input(this.welcomeMessage);

            if (command.equalsIgnoreCase("create")) {
                // CREATE voucher
                command = input.input("Choose your voucher type(FixedAmountVoucher = fixed, PercentDiscountVoucher = percent)");

                if (command.equalsIgnoreCase("fixed")) {
                    // Q1. Factory 구상체 지정을 이렇게 해 주는게 과연 맞는가?
                    voucherService.setVoucherRepository(new FixedAmountVoucherFactory());
                } else if (command.equalsIgnoreCase("percent")) {
                    // Q1. Factory 구상체 지정을 이렇게 해 주는게 과연 맞는가?
                    voucherService.setVoucherRepository(new PercentDiscountVoucherFactory());
                } else {
                    output.inputError("Invalid Input. You can create [fixed/percent] discount voucher");
                    continue;
                }

                command = input.input("How much of a discount do you want to give?");
                while (!MyUtils.isDigits(command)) {
                    output.inputError("Invalid Input. Only digits are allowed");
                    command = input.input("How much of a discount do you want to give?");
                }

                Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), Long.parseLong(command));
                output.printSuccessAddVoucher(voucher);

            } else if (command.equalsIgnoreCase("list")) {
                // LIST vouchers
                output.printAllVouchersInfo(voucherService.getAllVoucher());

            } else if (command.equalsIgnoreCase("exit")) {
                // EXIT program
                output.sayGoodBye();
                break;

            } else {
                // It is currently invalid input
                output.inputError("Invalid Input.");
            }
        } while (true);
    }
}
