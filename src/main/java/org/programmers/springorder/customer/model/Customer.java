package org.programmers.springorder.customer.model;

import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;

    private Customer(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public static Customer toCustomer(UUID customerId, String name, CustomerType customerType) {
        return new Customer(customerId, name, customerType);
    }

    public boolean sameCustomerId(UUID customerId){
        return this.customerId.equals(customerId);
    }
    public boolean isBlackList() {
        return this.customerType == CustomerType.BLACK;
    }

    public String insertCustomerDataInFile() {
        StringBuilder data = new StringBuilder();
        data.append(this.customerId).append(",")
                .append(this.name).append(",")
                .append(this.customerType.name());
        return data.toString();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name) && customerType == customer.customerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, customerType);
    }
}
