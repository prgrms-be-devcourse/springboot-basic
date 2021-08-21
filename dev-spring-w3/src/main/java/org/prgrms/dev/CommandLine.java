package org.prgrms.dev;

import org.prgrms.dev.io.Console;
import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.domain.FixedAmountVoucher;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

public class CommandLine implements Runnable {
    private Input input;
    private Output output;
    private ApplicationContext applicationContext;

    public CommandLine(Console input, Console output, ApplicationContext applicationContext) {
        this.input = input;
        this.output = output;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        while (true) {
            output.init();
            String command = input.input("> ");
            if (command.equals("create")) {
                output.voucherType();
                String type = input.input("> ");
                if (type.equals("f")) {
                    String inputString = input.input("input amount > ");
                    Long inputAmount = Long.valueOf(inputString);
                    voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), inputAmount));
                } else if (type.equals("p")) {
                    String inputString = input.input("input percent > ");
                    Long inputPercent = Long.valueOf(inputString);
                    voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), inputPercent));
                } else {
                    output.inputError();
                }
            } else if (command.equals("list")) {
                System.out.println(voucherService.listVoucher().toString());
            } else if (command.equals("exit")) {
                break;
            } else {
                output.inputError();
            }
        }
    }
}
