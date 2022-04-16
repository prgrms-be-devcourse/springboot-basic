package org.prgrms.springbasic.domain.customer;

import lombok.Getter;

import java.text.MessageFormat;
import java.util.UUID;

import static org.prgrms.springbasic.domain.customer.CustomerType.BLACK;

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
        return MessageFormat.format("{0},{1},{2}", customerId, customerType, name);
    }
}
