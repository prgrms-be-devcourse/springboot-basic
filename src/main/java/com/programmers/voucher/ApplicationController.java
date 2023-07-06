package com.programmers.voucher;

import com.programmers.voucher.controller.customer.CustomerController;
import com.programmers.voucher.controller.voucher.VoucherController;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.dto.Command;
import org.springframework.stereotype.Controller;

import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class ApplicationController implements Runnable {
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
    public void run() {
        AtomicBoolean running = new AtomicBoolean(true);

        while (running.get()) {
            output.displayCommands();
            Command command = input.readCommand();

            switch (command) {
                case EXIT -> running.set(false);
                case VOUCHER -> voucherController.run();
                case CUSTOMER -> customerController.run();
            }
        }
    }
}
