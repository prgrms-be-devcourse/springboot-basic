package com.prgrms.springbasic.domain.customer.controller;

import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerApiController {
    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CreateCustomerRequest request) {
        CustomerResponse customer = customerService.createCustomer(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{customerId}")
                .buildAndExpand(customer.customerId())
                .toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @GetMapping("/black-list")
    public ResponseEntity<List<CustomerResponse>> findBlackList() {
        return ResponseEntity.ok(customerService.findAllBlackList());
    }
}
