package com.programmers.customer.service;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.dto.CustomerRequestDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer create(CustomerRequestDto requestDto);

    List<Customer> findCustomers();

    Customer findCustomerById(UUID customerID);

    void deleteCustomerById(UUID customerID);
}
