package org.prgrms.kdtspringdemo.customer.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;

import java.io.IOException;


class CustomerControllerTest {
    private CustomerController customerController;
    private CustomerService customerService;

    @Test
    @DisplayName("블랙리스트 회원 리스트 조회")
    void printAllBlackListCustomer() throws IOException {
        customerService = new CustomerService();
        customerController = new CustomerController(customerService);
        customerController.printAllBlackListCustomer();
    }

}