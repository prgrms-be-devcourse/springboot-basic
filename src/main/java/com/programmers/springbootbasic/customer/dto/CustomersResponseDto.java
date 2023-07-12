package com.programmers.springbootbasic.customer.dto;

import com.programmers.springbootbasic.customer.domain.Customer;

import java.util.List;

public record CustomersResponseDto(List<Customer> customers) {

}