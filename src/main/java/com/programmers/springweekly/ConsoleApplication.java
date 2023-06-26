package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.view.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleApplication implements CommandLineRunner {

    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final Console console;

    private boolean running = true;

    @Override
    public void run(String... args) {
        while(running){
            console.outputProgramGuide();
            try {
                ProgramMenu selectMenu = ProgramMenu.findProgramMenu(console.inputMessage());

                switch(selectMenu){
                    case CREATE -> voucherController.createVoucher();
                    case LIST -> voucherController.getVoucherList();
                    case EXIT -> {
                        console.outputExitMessage();
                        running = false;
                    }
                    case CUSTOMER_BLACKLIST -> customerController.getBlackList();
                }

            } catch(Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
