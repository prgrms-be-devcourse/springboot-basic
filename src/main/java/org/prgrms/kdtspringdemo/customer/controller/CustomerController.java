package org.prgrms.kdtspringdemo.customer.controller;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final WalletService walletService;
    private final InputConsole inputConsole = new InputConsole();
    private final OutputConsole outputConsole = new OutputConsole();
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public void insert() {
        try {
            UUID customerId = java.util.UUID.randomUUID();
            outputConsole.getCustomerName();
            String name = inputConsole.getString();
            outputConsole.getCustomerIsBlack();
            Boolean isBlack = Boolean.parseBoolean(inputConsole.getString());

            customerService.insert(new Customer(customerId, name, isBlack));
            walletService.create(customerId); // 고객 생성 시 지갑 자동 생성
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
