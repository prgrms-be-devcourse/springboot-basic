package org.prgrms.weeklymission.customer.domain;

import lombok.Getter;

import java.util.UUID;

import static org.prgrms.weeklymission.customer.domain.CustomerType.BLACK;

@Getter
public class Customer {
    private final UUID customerId;
    private final CustomerType customerType;
    private final String name;

    public Customer(UUID customerId, CustomerType customerType, String name) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.name = name;
    }

    public static Customer blackCustomer(UUID customerId, String name) {
        return new Customer(customerId, BLACK, name);
    }

    @Override
    public String toString() {
        return "CustomerType: " + customerType +
                " CustomerID: " + customerId +
                " CustomerName: " + name;
    }
}
