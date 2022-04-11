package org.prgrms.kdtspringdemo.controller;

import org.prgrms.kdtspringdemo.io.Input;
import org.prgrms.kdtspringdemo.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class FrontController implements ApplicationRunner {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;

    public FrontController(Input input, Output output, VoucherController voucherController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
