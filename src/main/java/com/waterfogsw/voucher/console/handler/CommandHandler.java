package com.waterfogsw.voucher.console.handler;

import com.waterfogsw.voucher.console.Input;
import com.waterfogsw.voucher.console.Output;
import com.waterfogsw.voucher.global.FrontController;

public abstract class CommandHandler {
    protected final Input input;
    protected final Output output;
    protected final FrontController frontController;

    public CommandHandler(Input input, Output output, FrontController frontController) {
        this.input = input;
        this.output = output;
        this.frontController = frontController;
    }

    public abstract void handle();
}
