package org.programmers.springorder.customer.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.customer.dto.CustomerRequestDto;
import org.programmers.springorder.customer.service.CustomerService;
import org.programmers.springorder.utils.CustomerMenuType;
import org.programmers.springorder.utils.ExceptionHandler;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
    private final Console console;
    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            CustomerMenuType menu = ExceptionHandler.input(Console::inputCustomerMenu);

            switch (menu) {
                case CREATE -> createCustomer();
                case BLACK -> printBlackList();
                case BACK -> {
                    isRunning = false;
                    console.printMessage(Message.BACK_TO_MENU_MESSAGE);
                }
            }
        }
    }

    private void createCustomer() {
        CustomerRequestDto customerRequestDto = console.inputCustomerInfo();
        customerService.createCustomer(customerRequestDto);
        console.printMessage(Message.CUSTOMER_REGISTERED);
    }

    private void printBlackList() {
        console.showBlackList(customerService.getBlackList());
    }

}
