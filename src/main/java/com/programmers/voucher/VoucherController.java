package com.programmers.voucher;

import com.programmers.voucher.io.MenuType;
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
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            console.printOutput(Message.INTRO_MESSAGE);

            try {
                String inputMenu = console.getInput();

                switch (MenuType.getMenuType(inputMenu)) {
                    case EXIT:

                    case CREATE:

                    case LIST:

                }
            } catch (IllegalArgumentException e) {
                console.printOutput(Message.WRONG_ORDER_MESSAGE);
            }
        }
    }
}
