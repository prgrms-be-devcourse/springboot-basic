package org.prgrms.kdt.customer.controller;

import org.prgrms.kdt.app.io.InputHandler;
import org.prgrms.kdt.app.io.OutputHandler;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final OutputHandler outputHandler;
    private final InputHandler inputHandler;

    public CustomerController(CustomerService customerService, OutputHandler outputHandler, InputHandler inputHandler) {
        this.customerService = customerService;
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    public void getBlackList() {
        List<Customer> blackList = customerService.getBlackList();
        outputHandler.outputBlackList(blackList);
    }
}
