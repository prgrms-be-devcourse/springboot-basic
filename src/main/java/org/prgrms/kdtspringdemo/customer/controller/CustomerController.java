package org.prgrms.kdtspringdemo.customer.controller;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final OutputConsole outputConsole = new OutputConsole();

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void printAllBlackListCustomer() throws IOException {
        List<Customer> customerList = customerService.getBlackListCustomers();
        customerList.stream().forEach(customer -> outputConsole.printCustomer(customer));
    }
}
