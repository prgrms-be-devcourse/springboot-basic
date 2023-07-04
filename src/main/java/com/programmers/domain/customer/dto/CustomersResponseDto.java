package com.programmers.domain.customer.dto;

import com.programmers.domain.customer.Customer;

import java.util.List;

public record CustomersResponseDto(List<Customer> customers) {

}
