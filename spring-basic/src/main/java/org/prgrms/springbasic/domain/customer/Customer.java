package org.prgrms.springbasic.domain.customer;

import lombok.Getter;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.prgrms.springbasic.domain.customer.CustomerType.BLACK;
import static org.prgrms.springbasic.domain.customer.CustomerType.NORMAL;

@Getter
public class Customer {

    private final UUID customerId;
    private CustomerType customerType;
    private String name;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Customer(UUID customerId, CustomerType customerType, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    private Customer(UUID customerId, CustomerType customerType, String name, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Customer normalCustomer(UUID customerId, String name) {
        return new Customer(customerId, NORMAL, name, now());
    }

    public static Customer blackCustomer(UUID customerId, String name) {
        return new Customer(customerId, BLACK, name, now());
    }

    public Customer update(CustomerType customerType, String name) {
        this.customerType = (customerType == null) ? this.customerType : customerType;
        this.name = (name == null) ? this.name : name;
        this.modifiedAt = now();

        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},{1},{2},{3},{4}", customerId, customerType, name, createdAt, modifiedAt);
    }
}
