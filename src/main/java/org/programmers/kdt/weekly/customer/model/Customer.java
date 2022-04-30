package org.programmers.kdt.weekly.customer.model;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Customer {

    private final UUID customerId;
    private final String email;
    private final String customerName;
    private CustomerType customerType;

    public Customer(UUID customerId, String email, String customerName, CustomerType customerType) {
        if (!validatedEmail(email) || !validatedName(customerName)) {
            throw new IllegalArgumentException();
        }

        this.customerId = customerId;
        this.email = email;
        this.customerName = customerName;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public Customer changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
        return this;
    }

    @Override
    public String toString() {
        return
            "customerId: " + customerId +
                ", customerName: " + customerName +
                ", customerEmail: " +email+
                ", customerType: " + customerType;
    }

    public String serializeCustomer() {
        return customerId + "," + email + "," + customerName + "," + customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    public boolean validatedName(String name) {
        return name.isBlank();
    }

    public boolean validatedEmail(String email) {
        var regExp = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i";

        return Pattern.matches(regExp, email);
    }
}