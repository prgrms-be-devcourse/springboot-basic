package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.service.dto.Customer.CustomerCreationRequest;
import com.programmers.springbootbasic.service.dto.Customer.CustomerResponse;
import com.programmers.springbootbasic.service.dto.Customer.CustomerUpdateRequest;

import java.util.UUID;

public final class CustomerMapper {
    private CustomerMapper() {
    }

    public static Customer toCustomer(CustomerCreationRequest request) {
        return new Customer(UUID.randomUUID(), request.email(), request.name());
    }

    public static Customer toCustomer(CustomerUpdateRequest request) {
        return new Customer(UUID.randomUUID(), request.email(), request.name());
    }

    public static CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getCustomerId(), customer.getName(), customer.getEmail());
    }
}
