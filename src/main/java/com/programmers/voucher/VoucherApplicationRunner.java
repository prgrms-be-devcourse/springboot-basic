package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerConsoleController;
import com.programmers.voucher.domain.voucher.controller.VoucherConsoleController;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.command.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplicationRunner implements CommandLineRunner {
    private final Input input;
    private final Output output;
    private final VoucherConsoleController voucherController;
    private final CustomerConsoleController customerController;

    public VoucherApplicationRunner(Input input,
                                    Output output,
                                    VoucherConsoleController voucherController,
                                    CustomerConsoleController customerController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

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
