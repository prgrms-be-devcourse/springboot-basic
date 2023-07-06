package com.programmers.springweekly.dto.customer.response;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerResponse {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;

    public CustomerResponse(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.customerEmail = customer.getCustomerEmail();
        this.customerType = customer.getCustomerType();
    }
}
