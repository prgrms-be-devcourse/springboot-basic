package org.prgrms.voucher;

import org.prgrms.voucher.io.Input;
import org.prgrms.voucher.io.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class CommandLineApplication implements ApplicationRunner {

    private final Input input;
    private final Output output;
    private final Converter converter;

    public CommandLineApplication(Input input, Output output, Converter converter) {

        this.input = input;
        this.output = output;
        this.converter = converter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
