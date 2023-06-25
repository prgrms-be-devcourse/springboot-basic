package com.programmers.voucher.controller;

import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements Runnable {
    private final Input input;
    private final Output output;

    public VoucherController(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        output.displayCommands();
    }
}
