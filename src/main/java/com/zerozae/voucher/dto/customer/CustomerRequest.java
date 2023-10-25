package com.zerozae.voucher.dto.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerRequest {

    private String customerName;
    private CustomerType customerType;

    public CustomerRequest(String customerName, CustomerType customerType) {
        this.customerName = customerName;
        this.customerType = customerType;
    }

    public Customer of(UUID customerId) {
        return new Customer(customerId, customerName, customerType);
    }
}
