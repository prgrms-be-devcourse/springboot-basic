package com.zerozae.voucher.dto.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerCreateRequest {

    private String customerName;
    private CustomerType customerType;

    public CustomerCreateRequest(String customerName, CustomerType customerType) {
        this.customerName = customerName;
        this.customerType = customerType;
    }

    public Customer to(UUID customerId) {
        return new Customer(customerId, customerName, customerType);
    }
}
