package com.waterfogsw.voucher;

import com.waterfogsw.voucher.console.Input;
import com.waterfogsw.voucher.console.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class ConsoleApplication implements ApplicationRunner {

    private final Input input;
    private final Output output;
    private final ConsoleController consoleController;

    public ConsoleApplication(Input input, Output output, ConsoleController consoleController) {
        this.input = input;
        this.output = output;
        this.consoleController = consoleController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
