package com.programmers.voucher;

import com.programmers.voucher.io.Console;
import com.programmers.voucher.io.Message;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherController implements ApplicationRunner {

    private Console console;

    public VoucherController(Console console) {
        this.console = console;
    }

    @Override
    public void run(ApplicationArguments args) {
        console.printOutput(Message.INTRO_MESSAGE);
    }
}
