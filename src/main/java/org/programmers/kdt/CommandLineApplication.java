package org.programmers.kdt;

import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;
import org.programmers.kdt.usercommand.UserCommand;
import org.programmers.kdt.utils.MyUtils;
import org.programmers.kdt.voucher.*;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

public class CommandLineApplication implements Runnable {
    private final Input input;
    private final Output output;

    private final String welcomeMessage
            = "\n=== Voucher Program ===\nType create to create a voucher.\nType list to list all vouchers.\nType exit to exit the program.";
    private final String voucherTypeRequestMessage
            = "Choose your voucher type(FixedAmountVoucher = fixed, PercentDiscountVoucher = percent)";

    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        boolean termi = false;
        do {
            UserCommand command;
            try {
                command = UserCommand.of(input.input(this.welcomeMessage));
            } catch (RuntimeException e) {
                output.inputError("Invalid Command. Valid Command : " + List.of(UserCommand.values()));
                continue;
            }
            // String command = input.input(this.welcomeMessage);
            // TODO: "create", "list" 처럼 문자열을 직접 사용하지 않고, 상수 또는 ENUM 등을 사용하는 방식으로 바꿔보기
            switch (command) {
                case CREATE -> {
                    VoucherType voucherType;
                    try {
                        voucherType = VoucherType.of(input.input(voucherTypeRequestMessage));
                    } catch (RuntimeException e) {
                        output.inputError("Invalid Input. You can create " + List.of(VoucherType.values()) + " discount voucher");
                        continue;
                    }

                    String discount = input.input("How much of a discount do you want to give?");
                    while (!MyUtils.isDigits(discount)) {
                        output.inputError("Invalid Input. Only digits are allowed");
                        discount = input.input("How much of a discount do you want to give?");
                    }

                    Voucher voucher = voucherService.createVoucher(voucherType, UUID.randomUUID(), Long.parseLong(discount));
                    output.printSuccessAddVoucher(voucher);
                }
                case LIST -> output.printAllVouchersInfo(voucherService.getAllVoucher());
                case EXIT -> {
                    output.sayGoodBye();
                    termi = true;
                }
            }
        } while (!termi);
    }
}
