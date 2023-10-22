package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerRepository {
    public List<CustomerResponseDto> listBlockedCustomers();
}
