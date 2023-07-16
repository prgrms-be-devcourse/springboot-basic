package com.programmers.springweekly.domain.customer;

import com.programmers.springweekly.util.validator.CustomerValidator;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Customer {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;

    @Builder
    private Customer(UUID customerId, String customerName, String customerEmail, CustomerType customerType) {
        CustomerValidator.validateCustomer(customerName, customerEmail, customerType);

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }

}
