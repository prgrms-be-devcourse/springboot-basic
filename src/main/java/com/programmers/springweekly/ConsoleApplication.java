package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.view.Console;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final Console console;

    private boolean running = true;

    public ConsoleApplication(CustomerController customerController, VoucherController voucherController, Console console) {
        this.customerController = customerController;
        this.voucherController = voucherController;
        this.console = console;
    }

    @Override
    public void run(String... args) throws Exception {
        while(running){
            console.outputProgramGuide();
            ProgramMenu selectMenu = ProgramMenu.findProgramMenu(console.inputMessage());

            if(selectMenu == ProgramMenu.CREATE){
                voucherController.createVoucher();
            }

            if(selectMenu == ProgramMenu.LIST){
                voucherController.getVoucherList();
            }

            if(selectMenu == ProgramMenu.EXIT){
                console.outputExitMessage();
                running = false;
            }

            if(selectMenu == ProgramMenu.CUSTOMER_BLACKLIST){
                customerController.getCustomerBlackList();
            }
        }
    }
}
