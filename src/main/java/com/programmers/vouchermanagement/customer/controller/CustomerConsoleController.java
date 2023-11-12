package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;

@Profile("console")
@Controller
public class CustomerConsoleController {
    private final CustomerService customerService;

    public CustomerConsoleController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResponse create(CreateCustomerRequest createCustomerRequest) {
        return customerService.create(createCustomerRequest);
    }

    public List<CustomerResponse> readAll() {
        return customerService.readAll();
    }

    public List<CustomerResponse> readAllBlackCustomer() {
        return customerService.readAllBlackCustomer();
    }
}
