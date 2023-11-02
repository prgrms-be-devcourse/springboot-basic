package org.programmers.springorder.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.Message;
import org.programmers.springorder.dto.customer.CustomerRequestDto;
import org.programmers.springorder.service.CustomerService;
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
        CustomerMenuType menu;
        do {
            menu = ExceptionHandler.input(console::inputCustomerMenu);
            handleMenu(menu);
        } while (!menu.isBack());
    }

    private void handleMenu(CustomerMenuType menu) {
        switch (menu) {
            case CREATE -> createCustomer();
            case BLACK -> printBlackList();
            case BACK -> console.back();
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
