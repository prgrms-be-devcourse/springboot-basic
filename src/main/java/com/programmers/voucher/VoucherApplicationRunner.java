package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.command.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplicationRunner implements CommandLineRunner {
    private static boolean isRunning = true;

    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public VoucherApplicationRunner(Input input,
                                    Output output,
                                    VoucherController voucherController,
                                    CustomerController customerController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) {
        while (isRunning) {
            output.displayCommands();
            Command command = input.readCommand();

            switch (command) {
                case EXIT -> isRunning = false;
                case VOUCHER -> voucherController.run();
                case CUSTOMER -> customerController.run();
            }
        }
    }
}
