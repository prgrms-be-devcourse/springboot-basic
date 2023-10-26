package org.prgrms.kdtspringdemo.customer.controller;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.google.gson.internal.bind.TypeAdapters.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final InputConsole inputConsole = new InputConsole();
    private final OutputConsole outputConsole = new OutputConsole();
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void insert() {
        try {
            UUID customerId = java.util.UUID.randomUUID();
            outputConsole.getCustomerName();
            String name = inputConsole.getString();
            outputConsole.getCustomerIsBlack();
            Boolean isBlack = Boolean.parseBoolean(inputConsole.getString());

            customerService.insert(new Customer(customerId, name, isBlack));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void printAllCustomers() {
        List<Customer> customerList = customerService.findAll().get();
        customerList.stream().forEach(customer -> outputConsole.printCustomer(customer));
    }
    public void printAllBlackListCustomer() throws IOException {
        List<Customer> customerList = customerService.getBlackListCustomers();
        customerList.stream().forEach(customer -> outputConsole.printCustomer(customer));
    }
}
