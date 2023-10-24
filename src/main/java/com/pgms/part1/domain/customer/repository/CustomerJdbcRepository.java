package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.dto.CustomerFileResponseDto;

import java.util.List;

public class CustomerJdbcRepository implements CustomerRepository{
    @Override
    public List<CustomerFileResponseDto> listBlockedCustomers() {
        return null;
    }
}
