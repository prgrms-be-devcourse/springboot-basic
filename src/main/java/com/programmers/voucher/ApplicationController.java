package com.programmers.voucher;

import com.programmers.voucher.controller.VoucherController;
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

    public ApplicationController(Input input, Output output, VoucherController voucherController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
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
                case CUSTOMER -> voucherController.run();
            }
        }
    }
}
