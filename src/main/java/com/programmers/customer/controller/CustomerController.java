package com.programmers.customer.controller;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.domain.CustomerMapper;
import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.customer.service.CustomerService;

import java.util.List;

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResponseDto create(CustomerRequestDto requestDto) {
        Customer customer = customerService.create(requestDto);
        return CustomerMapper.convertDomainToResponseDto(customer);
    }

    public List<CustomerResponseDto> findAll() {
        return customerService.findCustomers().stream()
                .map(CustomerMapper::convertDomainToResponseDto)
                .toList();
    }
}
