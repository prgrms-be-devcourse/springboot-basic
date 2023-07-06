package com.programmers.customer.dto;

import com.programmers.customer.domain.Customer;

import java.util.List;

public record CustomersResponseDto(List<Customer> customers) {

}
