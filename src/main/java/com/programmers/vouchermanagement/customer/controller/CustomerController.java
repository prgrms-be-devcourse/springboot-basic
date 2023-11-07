package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
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
    public CustomerResponse create(CreateCustomerRequest createCustomerRequest) {
        return customerService.create(createCustomerRequest);
    }

    @GetMapping
    @ResponseBody
    public List<CustomerResponse> readAll() {
        return customerService.readAll();
    }

    @GetMapping("/blacklist")
    @ResponseBody
    public List<CustomerResponse> readAllBlackCustomer() {
        return customerService.readAllBlackCustomer();
    }
}
