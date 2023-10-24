package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.dto.CustomerFileResponseDto;

import java.util.List;

public interface CustomerRepository {
    public List<CustomerFileResponseDto> listBlockedCustomers();
}
