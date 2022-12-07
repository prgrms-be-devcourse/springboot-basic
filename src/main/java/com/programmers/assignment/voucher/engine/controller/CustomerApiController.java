package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.engine.service.CustomerService;
import com.programmers.assignment.voucher.util.dto.CustomerDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerApiController {

    private final CustomerService service;

    public CustomerApiController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> customerList() {
        return service.findCustomers();
    }

    @GetMapping("/api/v1/customers/{customerUuid}")
    public Customer customerDetails(@PathVariable UUID customerUuid) {
        return service.findCustomerByUuid(customerUuid);
    }

    @PostMapping("/api/v1/customers/new")
    public Customer createCustomer(@ModelAttribute CustomerDto customerDto) {
        return service.createCustomer(customerDto);
    }
}
