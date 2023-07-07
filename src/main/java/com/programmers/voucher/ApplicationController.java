package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.command.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationController implements CommandLineRunner {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ApplicationController(Input input,
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
        boolean running = true;

        while (running) {
            output.displayCommands();
            Command command = input.readCommand();

            switch (command) {
                case EXIT -> running = false;
                case VOUCHER -> voucherController.run();
                case CUSTOMER -> customerController.run();
            }
        }
    }
}
