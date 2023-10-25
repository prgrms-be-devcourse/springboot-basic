package com.prgrms.springbasic.domain.customer.controller;

import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }

    public List<CustomerResponse> findAllBlackLists() {
        return customerService.findAllBlackList();
    }
}
