package com.example.springbootbasic.dto;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;

public class CustomerDto {

    private Long customerId;
    private CustomerStatus status;

    public CustomerDto(Long customerId, CustomerStatus status) {
        this.customerId = customerId;
        this.status = status;
    }

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getStatus());
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
