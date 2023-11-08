package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("api")
@RequestMapping("/api/v1/customers")
@RestController
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @RequestBody
            CreateCustomerRequest createCustomerRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.create(createCustomerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> readAll(
            @RequestParam(name = "type", required = false)
            String type
    ) {
        if (type == null || type.equals("all"))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerService.readAll());

        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.readAllBlackCustomer());

    }
}
