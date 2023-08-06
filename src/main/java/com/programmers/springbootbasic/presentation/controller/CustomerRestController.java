package com.programmers.springbootbasic.presentation.controller;

import com.programmers.springbootbasic.presentation.controller.dto.Customer.CustomerCreationRequest;
import com.programmers.springbootbasic.presentation.controller.dto.Customer.CustomerUpdateRequest;
import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.service.dto.Customer.CustomerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createCustomer(@RequestBody @Valid CustomerCreationRequest request) {
        return customerService.createCustomer(request);
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findCustomer(@PathVariable @Valid @Email String email) {
        return customerService.findByEmail(email);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@PathVariable UUID id, @RequestBody @Valid CustomerUpdateRequest request) {
        customerService.updateCustomer(id, request);
    }
}
