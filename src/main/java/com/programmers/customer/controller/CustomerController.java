package com.programmers.customer.controller;

import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.customer.dto.CustomerUpdateRequest;
import com.programmers.customer.service.CustomerService;

import java.util.List;
import java.util.UUID;

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResponseDto create(CustomerRequestDto requestDto) {
        return customerService.create(requestDto);
    }

    public CustomerResponseDto update(CustomerUpdateRequest updateRequest) {
        return customerService.update(updateRequest);
    }

    public List<CustomerResponseDto> findAll() {
        return customerService.findCustomers();
    }

    public CustomerResponseDto findById(UUID customerId) {
        return customerService.findCustomerById(customerId);
    }

    public void deleteById(UUID customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
