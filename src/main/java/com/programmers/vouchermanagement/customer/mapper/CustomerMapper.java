package com.programmers.vouchermanagement.customer.mapper;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;

import java.util.UUID;

public class CustomerMapper {

    public static Customer toEntity(String[] customerData) {

        UUID customerId = UUID.fromString(customerData[0]);
        String name = customerData[1];
        CustomerType customerType = CustomerType.BLACK;

        return new Customer(customerId, name, customerType);
    }
}
