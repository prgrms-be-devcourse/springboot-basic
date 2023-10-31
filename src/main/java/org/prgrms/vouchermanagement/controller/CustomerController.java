package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.service.CustomerService;
import org.prgrms.vouchermanagement.view.Command;
import org.prgrms.vouchermanagement.view.ConsoleInput;
import org.prgrms.vouchermanagement.view.ConsoleOutput;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;
    private final CustomerService customerService;

    public CustomerController(ConsoleOutput consoleOutput, ConsoleInput consoleInput, CustomerService customerService) {
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
        this.customerService = customerService;
    }

    public void customer() {
        boolean notExitCommand = true;

        while (notExitCommand) {
            consoleOutput.printCustomerOptionMessage();
            Command command = consoleInput.commandInput();

            switch (command) {
                case LIST -> showList();
                case BLACKLIST -> showBlackList();
                case EXIT -> notExitCommand = exit();
            }
        }
    }

    private void showList() {
        List<Customer> customers = customerService.showCustomerList();
        consoleOutput.printCustomerList(customers);
    }

    private void showBlackList() {
        List<Customer> customers = customerService.showBlackCustomerList();
        consoleOutput.printBlackList(customers);
    }


    private boolean exit() {
        consoleOutput.printExitMessage();
        return false;
    }
}
