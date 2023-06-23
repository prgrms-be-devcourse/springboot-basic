package org.devcourse.voucher.console;

import org.devcourse.voucher.console.Console;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Console console = new Console();
        console.printMenu();
        String option = console.getOption();
    }
}
