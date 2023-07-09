package com.programmers.customer.service;

import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponseDto create(CustomerRequestDto requestDto);

    CustomerResponseDto update(CustomerRequestDto requestDto);

    List<CustomerResponseDto> findCustomers();

    CustomerResponseDto findCustomerById(UUID customerID);

    void deleteCustomerById(UUID customerID);
}
