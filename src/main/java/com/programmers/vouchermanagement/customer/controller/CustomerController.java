package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.dto.CustomerDto;
import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Profile({"api", "console"})
@RequestMapping("/api/v1/customers")
@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseBody
    public void create(CreateCustomerRequest createCustomerRequest) {
        customerService.create(createCustomerRequest);
    }

    @GetMapping("/blacklist")
    @ResponseBody
    public List<CustomerDto> readAllBlackCustomer() {
        return customerService.readAllBlackCustomer();
    }
}
