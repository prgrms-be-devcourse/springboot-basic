package com.devmission.springbootbasic;

import com.devmission.springbootbasic.view.ConsoleManager;
import com.devmission.springbootbasic.view.VoucherRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {

    ConsoleManager consoleManager;

    public CommandLineApplication(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    @Override
    public void run(String... args) throws Exception {
        Command command = consoleManager.readCommand();
        switch (command) {
            case CREATE:
                VoucherRequest voucherRequest = consoleManager.readVoucherType();
                break;

            case LIST:

                break;

            case EXIT:

        }
    }

}
