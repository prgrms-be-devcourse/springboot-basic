package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll(GetCustomersRequestDto request);
}
