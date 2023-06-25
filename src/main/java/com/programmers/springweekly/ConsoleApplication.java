package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.view.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final Console console;

    private final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);
    private boolean running = true;

    public ConsoleApplication(CustomerController customerController, VoucherController voucherController, Console console) {
        this.customerController = customerController;
        this.voucherController = voucherController;
        this.console = console;
    }

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
                    case CUSTOMER_BLACKLIST -> customerController.getCustomerBlackList();
                }

            } catch(Exception e){
                logger.error(e.getMessage());
            }
        }
    }
}
