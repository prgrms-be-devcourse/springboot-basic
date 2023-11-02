package com.programmers.vouchermanagement.customer.presentation;

import com.programmers.vouchermanagement.customer.application.CustomerService;
import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerResponseDto> readAllBlackList() {
        return customerService.readAllBlackList();
    }
}
